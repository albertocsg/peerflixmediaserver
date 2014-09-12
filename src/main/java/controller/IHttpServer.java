package controller;

public interface IHttpServer {
	enum SOURCE {
		ELITETORRENT, DIVXTOTAL, NEWPCT
	}

	public void init();
}
