package oper.config;

import org.springframework.stereotype.Component;

@Component("configImp")
public class ConfigImp implements IConfig {

	public String getValue(Keys key) {
		String value = "";
		
		switch (key) {
			case PEERFLIXPATH:
				value = "/usr/bin/peerflix";
				break;
				
			case DOWNLOADPATH:
				value = "/home/alberto/kk/file.0";
				break;
				
			case PORT:
				value = "8081";
				break;
		}
		
		return value;
	}

}
