package oper.webpage;

import java.util.List;

import model.Ficha;

public interface IDocumentsOper {

	public String getPageURL(int type, int page);
	
	public String getPageSearch(String search, int page);
	
	public List<Ficha> processPage(String page);
	
	public void getTorrent(Ficha ficha);
	
}
