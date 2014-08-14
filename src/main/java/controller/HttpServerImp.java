package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import model.Ficha;
import oper.config.IConfig;
import oper.config.IConfig.Keys;
import oper.exec.IRunPeerflix;
import oper.webpage.IDocumentsOper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@Component("httpServer")
public class HttpServerImp implements IHttpServer, HttpHandler {

	private HttpServer httpServer = null;
	private List<Ficha> fichas = null;

	@Autowired
	private IDocumentsOper documentsOperElite;

	@Autowired
	private IDocumentsOper documentsOperDivxTotal;

	@Autowired
	private IDocumentsOper documentsOperNewpct;

	@Autowired
	private IRunPeerflix runPeerflix;

	@Autowired
	@Qualifier("configImp")
	private IConfig config;

	private int numPage = 1;
	private String searchPage = "";
	private int typePage = 0;
	private int elementsPerPage = 16;
	private SOURCE currentSource = null;

	enum SOURCE {
		ELITETORRENT, DIVXTOTAL, NEWPCT
	}

	/**
	 * Constructor
	 */
	public HttpServerImp() {
	}

	public void init() {
		try {

			String httpPort = config.getValue(Keys.PORT);

			httpServer = HttpServer.create(new InetSocketAddress(Integer
					.valueOf(httpPort).intValue()), 0);
			httpServer.createContext("/", this);
			httpServer.setExecutor(null);
			httpServer.start();

			System.out.println("Server started at port " + httpPort + ".");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			httpServer = null;
		}
	}

	public void handle(HttpExchange exchange) throws IOException {

		OutputStream out = null;
		String response = null;

		if (exchange.getRequestURI().getPath().equals("/")
				|| exchange.getRequestURI().getPath().equals("/list")
				|| exchange.getRequestURI().getPath().equals("/cat_0_1")) {
			numPage = 0;
			typePage = 0;
			response = tryList(SOURCE.ELITETORRENT);
		} else if (exchange.getRequestURI().getPath().equals("/cat_1_1")) {
			numPage = 0;
			typePage = 1;
			response = tryList(SOURCE.ELITETORRENT);
		} else if (exchange.getRequestURI().getPath().equals("/cat_2_1")) {
			numPage = 0;
			typePage = 2;
			response = tryList(SOURCE.ELITETORRENT);
		} else if (exchange.getRequestURI().getPath().equals("/cat_3_1")) {
			numPage = 0;
			typePage = 3;
			response = tryList(SOURCE.ELITETORRENT);
		} else if (exchange.getRequestURI().getPath().equals("/cat_4_1")) {
			numPage = 0;
			typePage = 4;
			response = tryList(SOURCE.ELITETORRENT);
		} else if (exchange.getRequestURI().getPath().equals("/cat_5_1")) {
			numPage = 0;
			typePage = 5;
			response = tryList(SOURCE.ELITETORRENT);
		} else if (exchange.getRequestURI().getPath().equals("/cat_6_1")) {
			numPage = 0;
			typePage = 6;
			response = tryList(SOURCE.ELITETORRENT);
		} else if (exchange.getRequestURI().getPath().equals("/search_1")) {
			numPage = 0;
			typePage = 10;
			searchPage = exchange.getRequestURI().getQuery();
			response = trySearch(SOURCE.ELITETORRENT);
		} else if (exchange.getRequestURI().getPath().equals("/cat_0_2")) {
			numPage = 0;
			typePage = 0;
			response = tryList(SOURCE.DIVXTOTAL);
		} else if (exchange.getRequestURI().getPath().equals("/cat_1_2")) {
			numPage = 0;
			typePage = 1;
			response = tryList(SOURCE.DIVXTOTAL);
		} else if (exchange.getRequestURI().getPath().equals("/cat_2_2")) {
			numPage = 0;
			typePage = 2;
			response = tryList(SOURCE.DIVXTOTAL);
		} else if (exchange.getRequestURI().getPath().equals("/cat_3_2")) {
			numPage = 0;
			typePage = 3;
			response = tryList(SOURCE.DIVXTOTAL);
		} else if (exchange.getRequestURI().getPath().equals("/cat_4_2")) {
			numPage = 0;
			typePage = 4;
			response = tryList(SOURCE.DIVXTOTAL);
		} else if (exchange.getRequestURI().getPath().equals("/cat_5_2")) {
			numPage = 0;
			typePage = 5;
			response = tryList(SOURCE.DIVXTOTAL);
		} else if (exchange.getRequestURI().getPath().equals("/cat_6_2")) {
			numPage = 0;
			typePage = 6;
			response = tryList(SOURCE.DIVXTOTAL);
		} else if (exchange.getRequestURI().getPath().equals("/cat_7_2")) {
			numPage = 0;
			typePage = 7;
			response = tryList(SOURCE.DIVXTOTAL);
		} else if (exchange.getRequestURI().getPath().equals("/cat_8_2")) {
			numPage = 0;
			typePage = 8;
			response = tryList(SOURCE.DIVXTOTAL);
		} else if (exchange.getRequestURI().getPath().equals("/cat_9_2")) {
			numPage = 0;
			typePage = 9;
			response = tryList(SOURCE.DIVXTOTAL);
		} else if (exchange.getRequestURI().getPath().equals("/search_2")) {
			numPage = 0;
			typePage = 10;
			searchPage = exchange.getRequestURI().getQuery();
			response = trySearch(SOURCE.DIVXTOTAL);
		} else if (exchange.getRequestURI().getPath().equals("/cat_1_3")) {
			numPage = 0;
			typePage = 1;
			response = tryList(SOURCE.NEWPCT);
		} else if (exchange.getRequestURI().getPath().equals("/cat_2_3")) {
			numPage = 0;
			typePage = 2;
			response = tryList(SOURCE.NEWPCT);
		} else if (exchange.getRequestURI().getPath().equals("/cat_3_3")) {
			numPage = 0;
			typePage = 3;
			response = tryList(SOURCE.NEWPCT);
		} else if (exchange.getRequestURI().getPath().equals("/cat_4_3")) {
			numPage = 0;
			typePage = 4;
			response = tryList(SOURCE.NEWPCT);
		} else if (exchange.getRequestURI().getPath().equals("/cat_5_3")) {
			numPage = 0;
			typePage = 5;
			response = tryList(SOURCE.NEWPCT);
		} else if (exchange.getRequestURI().getPath().equals("/detail")) {
			response = tryDetail();
		} else if (exchange.getRequestURI().getPath().equals("/torrent")) {
			response = tryTorrent(exchange.getRequestURI().getQuery());
		} else if (exchange.getRequestURI().getPath().equals("/next")) {
			response = tryNext();
		} else if (exchange.getRequestURI().getPath().equals("/back")) {
			response = tryBack();
		} else {
			response = "Página no encontrada...";
		}

		exchange.sendResponseHeaders(200, response.length());

		out = exchange.getResponseBody();
		out.write(response.getBytes(), 0, response.length());
		out.flush();

		out.close();
	}

	private String tryList(SOURCE source) {
		StringBuilder response = new StringBuilder();
		List<Ficha> fichasTmp;
		String page;

		currentSource = source;
		IDocumentsOper docOper = getDocumentsOper(source);

		// If it's the first page, the refill the list of fichas
		if (numPage == 0) {
			fichas = new ArrayList<Ficha>();
			for (int i = 1; i <= 10; i++) {
				fichasTmp = null;
				page = docOper.getPageURL(typePage, i);
				fichasTmp = docOper.processPage(page);
				fichas.addAll(fichasTmp);
			}
			numPage = 1;
		}

		fichasTmp = getSelectedElementsByPage();

		response.append(prepareList(fichasTmp));

		return response.toString();
	}

	private String trySearch(SOURCE source) {
		StringBuilder response = new StringBuilder();
		List<Ficha> fichasTmp;

		currentSource = source;

		IDocumentsOper docOper = getDocumentsOper(source);

		// If it's the first page, the refill the list of fichas
		if (numPage == 0) {
			fichas = new ArrayList<Ficha>();
			for (int i = 1; i <= 10; i++) {
				String page = docOper.getPageSearch(searchPage, i);
				fichasTmp = docOper.processPage(page);
				fichas.addAll(fichasTmp);
			}
			numPage = 1;
		}

		fichasTmp = getSelectedElementsByPage();

		response.append(prepareList(fichasTmp));

		return response.toString();
	}

	private String tryDetail() {
		return "detail";
	}

	private String tryTorrent(String index) {
		StringBuilder response = new StringBuilder();
		int i = Integer.valueOf(index);
		i = i + ((numPage - 1) * elementsPerPage);

		IDocumentsOper docOper = getDocumentsOper(currentSource);

		if (fichas.get(i).getTorrent() == null) {
			docOper.getTorrent(fichas.get(i));
		}

		runPeerflix.run(fichas.get(i));

		response.append(getHtmlHeader());
		response.append("<table><tr>");
		response.append("<td><img src=\"").append(fichas.get(i).getImagen())
				.append("\" border=\"0\"></td>");
		response.append("<td>").append(fichas.get(i).getDetails())
				.append("</td></tr></table>");
		response.append("<br><br><a href=\"http://").append(getInternalIP())
				.append(":1234\" vod>Ver ").append(fichas.get(i).getNombre())
				.append("</a>");
		response.append(getHtmlFooter());

		return response.toString();
	}

	private String getInternalIP() {
		String ip = "";
		boolean salir = false;

		try {
			Enumeration<NetworkInterface> e = NetworkInterface
					.getNetworkInterfaces();

			while (e.hasMoreElements() && !salir) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration<InetAddress> ee = n.getInetAddresses();
				while (ee.hasMoreElements() && !salir) {
					InetAddress i = (InetAddress) ee.nextElement();
					if (i instanceof Inet4Address && !i.isLoopbackAddress()) {
						ip = i.getHostAddress();
						salir = true;
					}
				}
			}
		} catch (SocketException e1) {
			e1.printStackTrace();
		}

		if (ip.isEmpty()) {
			ip = "127.0.0.1";
		}

		return ip;
	}

	private String getHtmlHeader() {
		StringBuilder header = new StringBuilder();
		header.append("<html>\n")
				.append("<head>\n")
				.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">\n")
				.append("<meta SYABAS-FULLSCREEN>\n")
				.append("</head>\n")
				.append("<body bgcolor=\"#5782BA\" text=\"#f0f8ff\" link=\"#f0f8ff\" alink=\"#ffd700\">\n");
		return header.toString();
	}

	private String getHtmlFooter() {
		StringBuilder footer = new StringBuilder();
		footer.append("</body>\n</html>\n");
		footer.append("                                             ");
		return footer.toString();
	}

	private String getCategories() {
		StringBuilder response = new StringBuilder();

		response.append("ELITETORRENT: ");
		response.append("<a href=\"./cat_0_1\">Todos</a> | \n");
		response.append("<a href=\"./cat_1_1\">Estrenos</a> | \n");
		response.append("<a href=\"./cat_2_1\">Peliculas</a> | \n");
		response.append("<a href=\"./cat_3_1\">HDRIP</a> | \n");
		response.append("<a href=\"./cat_4_1\">MicroHD</a> | \n");
		response.append("<a href=\"./cat_5_1\">Series</a> | \n");
		response.append("<a href=\"./cat_6_1\">Documentales y TV</a> | \n");
		response.append("<a href=\"./search_1?banshee\">Banshee</a> | \n");
		response.append("<a href=\"./search_1?big+bang+theory\">Big Bang Theory</a>\n");
		response.append("<br>\n");
		response.append("DIVXTOTAL: ");
		response.append("<a href=\"./cat_0_2\">Todos</a> | \n");
		response.append("<a href=\"./cat_1_2\">Animación</a> | \n");
		response.append("<a href=\"./cat_2_2\">Acción</a> | \n");
		response.append("<a href=\"./cat_3_2\">Aventura</a> | \n");
		response.append("<a href=\"./cat_4_2\">Comedia</a> | \n");
		response.append("<a href=\"./cat_5_2\">Drama</a> | \n");
		response.append("<a href=\"./cat_6_2\">Sci-fi</a> | \n");
		response.append("<a href=\"./cat_7_2\">Terror</a> | \n");
		response.append("<a href=\"./cat_8_2\">Thriller</a> | \n");
		response.append("<a href=\"./cat_9_2\">Españolas</a>\n");
		response.append("<br>\n");
		response.append("NEWPCT: ");
		response.append("<a href=\"./cat_1_3\">DVDRip BRRip</a> | \n");
		response.append("<a href=\"./cat_2_3\">Estrenos</a> | \n");
		response.append("<a href=\"./cat_3_3\">MicroHD</a> | \n");
		response.append("<a href=\"./cat_4_3\">Series HDTV</a> | \n");
		response.append("<a href=\"./cat_5_3\">Series HD</a> | \n");
		response.append("<br><br>\n");

		return response.toString();
	}

	private String prepareList(List<Ficha> fichas) {
		StringBuilder response = new StringBuilder();
		int maxColumns = 8;

		response.append(getHtmlHeader());

		if (runPeerflix.isRunning()) {
			response.append("<a href=\"http://").append(getInternalIP())
					.append(":1234\" vod>Ver ")
					.append(runPeerflix.getFicha().getNombre())
					.append("</a><br><br>");
		}

		response.append("<a href=\"./next\">Siguiente</a> | ");
		response.append(numPage);
		response.append(" | <a href=\"./back\">Anterior</a><br><br>");
		response.append(getCategories());

		if (fichas != null) {
			response.append("<table>");
			int col = 0;
			int i = 0;
			String nombre = null;
			for (Ficha ficha : fichas) {
				if (ficha != null) {
					if (col == 0) {
						response.append("<tr>\n");
					}
					nombre = ficha.getNombre();
					if (nombre.length() > 50) {
						nombre = nombre.substring(0, 50) + "...";
					}
					response.append(
							"<td valign=\"top\"><table><tr><td><img src=\"")
							.append(ficha.getImagen())
							.append("\" border=\"0\" width=120 height=170></td></tr><tr><td>\n")
							.append("<a href=\"./torrent?").append(i)
							.append("\">").append(nombre)
							.append("</a></td></tr></table><td>\n");
					col++;
					i++;
					if (col == maxColumns) {
						response.append("</tr>\n");
						col = 0;
					}
				}
			}
			response.append("</table>\n");
		}
		response.append(getHtmlFooter());

		return response.toString();
	}

	private String tryNext() {
		numPage++;
		if (typePage == 10) {
			return trySearch(currentSource);
		} else {
			return tryList(currentSource);
		}
	}

	private String tryBack() {
		numPage--;
		if (numPage < 1) {
			numPage = 1;
		}
		if (typePage == 10) {
			return trySearch(currentSource);
		} else {
			return tryList(currentSource);
		}
	}

	private List<Ficha> getSelectedElementsByPage() {
		int firstElement = ((numPage - 1) * elementsPerPage) + 1;
		List<Ficha> selection = new ArrayList<Ficha>();

		for (int i = 0; i < elementsPerPage; i++) {
			if (i + firstElement > fichas.size()) {
				break;
			}
			selection.add(fichas.get(firstElement + i - 1));
		}

		return selection;
	}

	private IDocumentsOper getDocumentsOper(SOURCE source) {
		IDocumentsOper docOper = documentsOperElite;

		switch (source) {
		case ELITETORRENT:
			docOper = documentsOperElite;
			break;

		case DIVXTOTAL:
			docOper = documentsOperDivxTotal;
			break;

		case NEWPCT:
			docOper = documentsOperNewpct;
			break;
		}

		return docOper;

	}

}
