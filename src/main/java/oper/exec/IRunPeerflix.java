package oper.exec;

import model.Ficha;

public interface IRunPeerflix {
	
	public void run(Ficha ficha);
	
	public void stop();
	
	public Ficha getFicha();
	
	public boolean isRunning();

}
