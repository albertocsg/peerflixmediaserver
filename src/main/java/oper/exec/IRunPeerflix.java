package oper.exec;

import model.Ficha;

public interface IRunPeerflix {
	
	public void run(Ficha ficha, int element);
	
	public void stop();
	
	public Ficha getFicha();
	
	public boolean isRunning();
	
	/**
	 * Get all the elements inside of the "ficha".
	 * @param ficha
	 * @return Array of String with the elements.
	 */
	public String[] getElements(Ficha ficha);

}
