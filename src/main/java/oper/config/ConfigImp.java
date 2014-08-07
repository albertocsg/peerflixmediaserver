package oper.config;

import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

@Component("configImp")
public class ConfigImp implements IConfig {

	public String getValue(Keys key) {
		String value = "";
		ResourceBundle rb = ResourceBundle.getBundle("config");
		
		// If the config file is null, then return null.
		if (rb == null) {
			return null;
		}
		
		// Read the config key from the config.properties.
		switch (key) {
			case PEERFLIXPATH:
				value = rb.getString("peerflixpath");
				break;
				
			case DOWNLOADPATH:
				value = rb.getString("downloadpath");
				break;
				
			case PORT:
				value = rb.getString("port");
				break;
		}
		
		return value;
	}

}
