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

	public String getPageURL(int typeDoc, int page) {
		StringBuilder document = new StringBuilder();
		try {
			
			URL url = null;
			
			switch (typeDoc) {
			
				case 1:	// estrenos
					url = new URL("http://www.elitetorrent.net/categoria/1/estrenos/pag:" + page);
					break;
					
				case 2: // peliculas
					url = new URL("http://www.elitetorrent.net/categoria/2/peliculas/pag:" + page);
					break;
					
				case 3: // HDRIP
					url = new URL("http://www.elitetorrent.net/categoria/13/peliculas-hdrip/pag:" + page);
					break;
					
				case 4: // microHD
					url = new URL("http://www.elitetorrent.net/categoria/17/peliculas-microhd/pag:" + page);
					break;
					
				case 5: // series
					url = new URL("http://www.elitetorrent.net/categoria/4/series/pag:" + page);
					break;
					
				case 6:	// docus y tv
					url = new URL("http://www.elitetorrent.net/categoria/6/docus-y-tv/pag:" + page);
					break;
					
				default:	// all
					url = new URL("http://www.elitetorrent.net/descargas/pag:" + page);
					break;
			
			}
			
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
	
	public void getTorrent(Ficha ficha) {
		if (ficha == null) {
			return;
		}
		
		try {
			
			Document doc = Jsoup.parse(new URL(ficha.getUrl()), 10000);
			String magnet = doc.getElementsByClass("enlace_torrent").get(1).attr("href");
			String details = doc.getElementsByClass("detalles").get(0).html();
			System.out.println(details);
			
			ficha.setTorrent(magnet);
			ficha.setDetails(details);
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getPageSearch(String search, int page) {
		StringBuilder document = new StringBuilder();
		try {
			
			URL url = null;
			String searchText = search;
			searchText.replace(" ", "+");
			
			url = new URL("http://www.elitetorrent.net/busqueda/" + searchText + "/pag:" + page);
			
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
	
}
