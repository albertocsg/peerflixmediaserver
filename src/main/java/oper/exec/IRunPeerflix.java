package oper.exec;

import model.Ficha;

public interface IRunPeerflix {
	
	public void run(Ficha ficha, int element);
	
	public void stop();
	
	public Ficha getFicha();
	
	public boolean isRunning();
	
	public String[] getElements(Ficha ficha);

}
