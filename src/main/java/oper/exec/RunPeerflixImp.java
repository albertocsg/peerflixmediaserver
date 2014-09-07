package oper.exec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import model.Ficha;
import oper.config.IConfig;
import oper.config.IConfig.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RunPeerflixImp implements IRunPeerflix {

	@Autowired
	@Qualifier("configImp")
	private IConfig config;

	private Process peerflixProcess = null;
	private Ficha fichaRunning = null;
	
	public RunPeerflixImp() {
		peerflixProcess = null;
		fichaRunning = null;
	}

	public void run(Ficha ficha, int element) {
		// If the ficha is the same that the one that is running, then do nothing.
		if (fichaRunning != null && ficha != null && fichaRunning.getTorrent().equals(ficha.getTorrent())) {
			return;
		}
		
		// If there is another peerflix running, then stop it.
		if (isRunning()) {
			stop();
		}
		
		try {
			fichaRunning = ficha;
			String peerflixPath = config.getValue(Keys.PEERFLIXPATH);
			String downloadPath = config.getValue(Keys.DOWNLOADPATH);
			if (element >= 0) {
				launchProcess(false, peerflixPath, ficha.getTorrent(), 
						"--quiet", "--remove",
						"--port", "1234", 
						"--path", downloadPath, 
						"--index", String.valueOf(element));
			} else {
				launchProcess(false, peerflixPath, ficha.getTorrent(), 
						"--quiet", "--remove",
						"--port", "1234", 
						"--path", downloadPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
			peerflixProcess = null;
			fichaRunning = null;
		}
	}

	public void stop() {
		if (peerflixProcess != null) {
			try {
				peerflixProcess.destroy();
				peerflixProcess.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				peerflixProcess = null;
				fichaRunning = null;
			}
		}

	}

	public Ficha getFicha() {
		return fichaRunning;
	}

	public boolean isRunning() {
		return peerflixProcess != null;
	}

	public String[] getElements(Ficha ficha) {
		String output = null;
		String[] elements = null;
		
		try {
			String peerflixPath = config.getValue(Keys.PEERFLIXPATH);
			output = launchProcess(true, peerflixPath, ficha.getTorrent(), "--list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Transform the output to a String[]
		elements = processElementsToList(output);
		
		return elements;
	}
	
	
	// -------------------------
	// ---- PRIVATE METHODS ----
	// -------------------------
	
	private String launchProcess(boolean mustWait, String... command) throws IOException {
		StringBuilder output = new StringBuilder();
		
		 Process process = new ProcessBuilder(command).start();
		 
		if (mustWait) {
			InputStreamReader inpStrd = new InputStreamReader(process.getInputStream());
			BufferedReader buffRd = new BufferedReader(inpStrd);
			
			String line = null;			
			while((line = buffRd.readLine()) != null) {
				output.append(line).append("\n");
			}
			
			buffRd.close();
		} else {
			peerflixProcess = process;
		}
		
		return output.toString();
	}

	/**
	 * Process the output of peerflix list command and transforms the elements to a array.
	 * @param output the output of the peerflix list command.
	 * @return the array of elements or null.
	 */
	private String[] processElementsToList(String output) {
		String[] elements = null;
		if (output != null) {
			StringTokenizer tokens = new StringTokenizer(output, ":\n");
			ArrayList<String> values = new ArrayList<String>();
			String line;
			
			while (tokens.hasMoreTokens()) {
				
				// Ignore the first token
				line = tokens.nextToken();
				
				if (tokens.hasMoreTokens()) {
					// Get the next token
					line = tokens.nextToken();
					line = line.substring(2, line.indexOf("[39m")-1);
					line = line.replace("[35m", "");
					values.add(line);
				}
				
			}
			
			elements = (String[]) values.toArray(new String[values.size()]);
		}
		
		return elements;
		
	}

}
