package oper.exec;

import java.io.IOException;

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

	private static Process peerflixProcess = null;
	private static Ficha fichaRunning = null;

	public void run(Ficha ficha) {
		// If the ficha is the same that the one that is running, then do nothing.
		if (fichaRunning != null && ficha != null && fichaRunning.getTorrent().equals(ficha.getTorrent())) {
			return;
		}
		
		// If there is another peerflix running, then it stop it.
		if (isRunning()) {
			stop();
		}
		
		try {
			fichaRunning = ficha;
			String peerflixPath = config.getValue(Keys.PEERFLIXPATH);
			String downloadPath = config.getValue(Keys.DOWNLOADPATH);
			peerflixProcess = new ProcessBuilder(peerflixPath, ficha.getTorrent(), "--quiet", "--remove",
					"--port", "1234", "--path", downloadPath).start();
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
	

}
