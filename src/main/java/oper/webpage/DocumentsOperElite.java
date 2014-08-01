package oper.webpage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import model.Ficha;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class DocumentsOperElite implements IDocumentsOper {

	public String getPageURL(int page) {
		StringBuilder document = new StringBuilder();
		try {
			
			URL url = new URL("http://www.elitetorrent.net/descargas/pag:" + page);
			
			URLConnection conn = url.openConnection();
			BufferedReader entrada = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			
			String linea;
			
			while ((linea = entrada.readLine()) != null) {
				document.append(linea);
			}

			entrada.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return document.toString();
		
	}

	public List<Ficha> processPage(String page) {
		Document doc = Jsoup.parse(page);
		Element content = doc.getElementById("cuerpo");
		Elements elements = content.getElementsByTag("li");
		ArrayList<Ficha> fichas = new ArrayList<Ficha>();
		for (Element e : elements) {
			Ficha ficha = new Ficha();
			ficha.setNombre(e.getElementsByTag("a").get(1).text());
			ficha.setImagen(e.getElementsByTag("img").get(0).attr("abs:src"));
			ficha.setUrl(e.getElementsByTag("a").get(1).attr("abs:href"));
			fichas.add(ficha);
		}
		return fichas;
	}
	
	public String getTorrent(String url) {
		try {
			
			Document doc = Jsoup.parse(new URL(url), 10000);
			String magnet = doc.getElementsByClass("enlace_torrent").get(1).attr("href");
			
			return magnet;
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
