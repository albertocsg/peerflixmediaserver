package oper.exec;

import java.io.IOException;

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

	public void run(String url) {
		try {
			String peerflixPath = config.getValue(Keys.PEERFLIXPATH);
			String downloadPath = config.getValue(Keys.DOWNLOADPATH);
			peerflixProcess = new ProcessBuilder(peerflixPath, url, "--quiet", "--remove",
					"--port", "1234", "--path", downloadPath).start();
		} catch (IOException e) {
			e.printStackTrace();
			peerflixProcess = null;
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
			}
		}

	}

}
