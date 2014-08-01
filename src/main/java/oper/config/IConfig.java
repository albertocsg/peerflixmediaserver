package oper.config;

public interface IConfig {

	public enum Keys {
		PEERFLIXPATH,
		DOWNLOADPATH,
		PORT
	};
	
	/**
	 * Get config value form the key.
	 * @param key
	 * @return value
	 */
	public String getValue(Keys key);
	
}
