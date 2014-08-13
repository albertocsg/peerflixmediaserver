package oper.webpage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
		String url = null;

		switch (typeDoc) {

		case 1: // estrenos
			url = "http://www.elitetorrent.net/categoria/1/estrenos/pag:"
					+ page;
			break;

		case 2: // peliculas
			url = "http://www.elitetorrent.net/categoria/2/peliculas/pag:"
					+ page;
			break;

		case 3: // HDRIP
			url = "http://www.elitetorrent.net/categoria/13/peliculas-hdrip/pag:"
					+ page;
			break;

		case 4: // microHD
			url = "http://www.elitetorrent.net/categoria/17/peliculas-microhd/pag:"
					+ page;
			break;

		case 5: // series
			url = "http://www.elitetorrent.net/categoria/4/series/pag:" + page;
			break;

		case 6: // docus y tv
			url = "http://www.elitetorrent.net/categoria/6/docus-y-tv/pag:"
					+ page;
			break;

		default: // all
			url = "http://www.elitetorrent.net/descargas/pag:" + page;
			break;

		}

		return getPage(url);

	}

	public List<Ficha> processPage(String page) {
		Document doc = Jsoup.parse(page);
		Element content = doc.getElementById("cuerpo");
		Elements elements = content.getElementsByTag("li");
		ArrayList<Ficha> fichas = new ArrayList<Ficha>();
		for (Element e : elements) {
			Ficha ficha = new Ficha();
			ficha.setNombre(e.getElementsByTag("a").get(1).text());
			String image = e.getElementsByTag("img").get(0).attr("abs:src");
			image = image.replace(".jpg", "_g.jpg");
			ficha.setImagen(image);
			ficha.setUrl(e.getElementsByTag("a").get(1).attr("abs:href"));
			fichas.add(ficha);
		}
		return fichas;
	}

	public void getTorrent(Ficha ficha) {
		if (ficha == null) {
			return;
		}

		String page = getPage(ficha.getUrl());

		Document doc = Jsoup.parse(page);
		String magnet = doc.getElementsByClass("enlace_torrent").get(1)
				.attr("href");
		String details = doc.getElementsByClass("detalles").get(0).html();

		ficha.setTorrent(magnet);
		ficha.setDetails(details);
	}

	public String getPageSearch(String search, int page) {
		String searchText = search;
		searchText.replace(" ", "+");

		String url = "http://www.elitetorrent.net/busqueda/" + searchText
				+ "/pag:" + page;

		return getPage(url);
	}

	/**
	 * Get the html of the given page.
	 * 
	 * @param page
	 *            URL
	 * @return String with the html
	 */
	private String getPage(String page) {
		StringBuilder document = new StringBuilder();
		try {

			URL url = new URL(page);

			URLConnection conn = url.openConnection();
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));

			String linea;

			while ((linea = entrada.readLine()) != null) {
				document.append(linea);
			}

			entrada.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return document.toString();

	}

}
