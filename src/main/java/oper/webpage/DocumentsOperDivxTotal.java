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
public class DocumentsOperDivxTotal implements IDocumentsOper {

	public String getPageURL(int typeDoc, int page) {
		String url;
		switch (typeDoc) {

		case 1: // Animación
			url = "http://www.divxtotal.com/peliculas/genero-114/pagina/"
					+ page + "/";
			break;

		case 2: // Acción
			url = "http://www.divxtotal.com/peliculas/genero-101/pagina/"
					+ page + "/";
			break;

		case 3: // Aventura
			url = "http://www.divxtotal.com/peliculas/genero-103/pagina/"
					+ page + "/";
			break;

		case 4: // Comedia
			url = "http://www.divxtotal.com/peliculas/genero-104/pagina/"
					+ page + "/";
			break;

		case 5: // Drama
			url = "http://www.divxtotal.com/peliculas/genero-106/pagina/"
					+ page + "/";
			break;

		case 6: // Sci-fi
			url = "http://www.divxtotal.com/peliculas/genero-107/pagina/"
					+ page + "/";
			break;

		case 7: // Terror
			url = "http://www.divxtotal.com/peliculas/genero-108/pagina/"
					+ page + "/";
			break;

		case 8: // Thriller
			url = "http://www.divxtotal.com/peliculas/genero-109/pagina/"
					+ page + "/";
			break;

		case 9: // Españolas
			url = "http://www.divxtotal.com/peliculas/genero-112/pagina/"
					+ page + "/";
			break;

		default: // all
			url = "http://www.divxtotal.com/peliculas/pagina/" + page + "/";
			break;

		}

		return getPage(url);

	}

	public List<Ficha> processPage(String page) {
		Document doc = Jsoup.parse(page);
		Elements elements = doc.getElementsByClass("seccontnom");
		ArrayList<Ficha> fichas = new ArrayList<Ficha>();
		for (Element e : elements) {
			Ficha ficha = new Ficha();
			ficha.setNombre(e.getElementsByTag("a").get(0).text());
			ficha.setUrl("http://www.divxtotal.com" + e.getElementsByTag("a").get(0).attr("href"));

			String image = e.getElementsByTag("a").get(0).attr("href");
			image = image.replaceAll("/peliculas/torrent/", "");
			image = image.replace("/", ".");
			image = "http://www.divxtotal.com/torrents_img/t" + image + "jpg";
			ficha.setImagen(image);

			fichas.add(ficha);
		}
		return fichas;
	}

	public void getTorrent(Ficha ficha) {
		// Check if ficha is not null and if has url.
		if (ficha != null & ficha.getUrl() != null && !ficha.getUrl().equals("")) {

			String page = getPage(ficha.getUrl());

			Document doc = Jsoup.parse(page);
			Elements elements = doc.getElementsByClass("ficha_link_det");
			ficha.setTorrent("http://www.divxtotal.com" + elements.get(0).getElementsByTag("a").get(0)
					.attr("href"));
		}
	}

	public String getPageSearch(String search, int page) {
		StringBuilder document = new StringBuilder();
		try {

			URL url = null;
			String searchText = search;
			searchText.replace(" ", "+");

			url = new URL("http://www.divxtotal.com/buscar.php?busqueda=" + searchText
					+ "&pagina=" + page);

			URLConnection conn = url.openConnection();
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

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

	/**
	 * Get the html of the given page.
	 * @param page URL
	 * @return String with the html
	 */
	private String getPage(String page) {
		StringBuilder document = new StringBuilder();
		try {

			URL url = new URL(page);
			;

			URLConnection conn = url.openConnection();
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

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

	/**
	 * Get and update the image and torrent uri of the given ficha
	 * @param ficha
	 */
	private void getImageAndTorrent(Ficha ficha) {
		// Check if ficha is not null and if has url.
		if (ficha != null & ficha.getUrl() != null && !ficha.getUrl().equals("")) {

			String page = getPage(ficha.getUrl());

			Document doc = Jsoup.parse(page);
			Elements elements = doc.getElementsByClass("ficha_img");
			ficha.setImagen("http://www.divxtotal.com" + elements.get(0).getElementsByTag("img").get(0)
					.attr("src"));
			elements = doc.getElementsByClass("ficha_link_det");
			ficha.setTorrent("http://www.divxtotal.com" + elements.get(0).getElementsByTag("a").get(0)
					.attr("href"));
		}
	}

}
