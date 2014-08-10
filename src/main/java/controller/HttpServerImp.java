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
	@Qualifier("documentsOperElite")
	private IDocumentsOper documentsOper;

	@Autowired
	private IRunPeerflix runPeerflix;

	@Autowired
	@Qualifier("configImp")
	private IConfig config;

	enum HTMLTYPE {
		NORMAL, POPCORNHOUR
	}

	private int numPage = 1;
	private String searchPage = "";
	private int typePage = 0;
	private HTMLTYPE htmlType = HTMLTYPE.NORMAL;
	private int elementsPerPage = 16;

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

		if (exchange.getRequestURI().getPath().equals("/list")) {
			numPage = 1;
			typePage = 0;
			htmlType = HTMLTYPE.NORMAL;
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/estrenos")) {
			numPage = 1;
			typePage = 1;
			htmlType = HTMLTYPE.NORMAL;
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/peliculas")) {
			numPage = 1;
			typePage = 2;
			htmlType = HTMLTYPE.NORMAL;
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/hdrip")) {
			numPage = 1;
			typePage = 3;
			htmlType = HTMLTYPE.NORMAL;
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/microhd")) {
			numPage = 1;
			typePage = 4;
			htmlType = HTMLTYPE.NORMAL;
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/series")) {
			numPage = 1;
			typePage = 5;
			htmlType = HTMLTYPE.NORMAL;
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/docusytv")) {
			numPage = 1;
			typePage = 6;
			htmlType = HTMLTYPE.NORMAL;
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/search")) {
			numPage = 1;
			typePage = 10;
			htmlType = HTMLTYPE.NORMAL;
			searchPage = exchange.getRequestURI().getQuery();
			response = trySearch();
		} else if (exchange.getRequestURI().getPath().equals("/listpch")) {
			numPage = 1;
			typePage = 0;
			htmlType = HTMLTYPE.POPCORNHOUR;
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/estrenospch")) {
			numPage = 1;
			typePage = 1;
			htmlType = HTMLTYPE.POPCORNHOUR;
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/peliculaspch")) {
			numPage = 1;
			typePage = 2;
			htmlType = HTMLTYPE.POPCORNHOUR;
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/hdrippch")) {
			numPage = 1;
			typePage = 3;
			htmlType = HTMLTYPE.POPCORNHOUR;
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/microhdpch")) {
			numPage = 1;
			typePage = 4;
			htmlType = HTMLTYPE.POPCORNHOUR;
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/seriespch")) {
			numPage = 1;
			typePage = 5;
			htmlType = HTMLTYPE.POPCORNHOUR;
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/docusytvpch")) {
			numPage = 1;
			typePage = 6;
			htmlType = HTMLTYPE.POPCORNHOUR;
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/searchpch")) {
			numPage = 1;
			typePage = 10;
			htmlType = HTMLTYPE.POPCORNHOUR;
			searchPage = exchange.getRequestURI().getQuery();
			response = trySearch();
		} else if (exchange.getRequestURI().getPath().equals("/detail")) {
			response = tryDetail();
		} else if (exchange.getRequestURI().getPath().equals("/torrent")) {
			response = tryTorrent(exchange.getRequestURI().getQuery(),
					HTMLTYPE.NORMAL);
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

	private String tryList() {
		StringBuilder response = new StringBuilder();
		List<Ficha> fichasTmp;
		
		// If it's the first page, the refill the list of fichas
		if (numPage == 1) {
			fichas = new ArrayList<Ficha>();
			for (int i = 1; i <= 10; i++) {
				String page = documentsOper.getPageURL(typePage, i);
				fichasTmp = documentsOper.processPage(page);
				fichas.addAll(fichasTmp);
			}
		}
		
		fichasTmp = getSelectedElementsByPage();
		
		response.append(prepareList(fichasTmp, htmlType));

		return response.toString();
	}

	private String trySearch() {
		StringBuilder response = new StringBuilder();
		List<Ficha> fichasTmp;
		
		// If it's the first page, the refill the list of fichas
		if (numPage == 1) {
			fichas = new ArrayList<Ficha>();
			for (int i = 1; i <= 10; i++) {
				String page = documentsOper.getPageSearch(searchPage, i);
				fichasTmp = documentsOper.processPage(page);
				fichas.addAll(fichasTmp);
			}
		}

		fichasTmp = getSelectedElementsByPage();
		
		response.append(prepareList(fichasTmp, htmlType));

		return response.toString();
	}

	private String tryDetail() {
		return "detail";
	}

	private String tryTorrent(String index, HTMLTYPE type) {
		StringBuilder response = new StringBuilder();
		int i = Integer.valueOf(index);
		i = i + ((numPage - 1) * elementsPerPage);

		if (fichas.get(i).getTorrent() == null) {
			documentsOper.getTorrent(fichas.get(i));
		}

		runPeerflix.run(fichas.get(i));

		response.append(getHtmlHeader(type));
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

	private String getHtmlHeader(HTMLTYPE type) {
		StringBuilder header = new StringBuilder();
		if (type == HTMLTYPE.NORMAL) {
			header.append("<html>")
					.append("<head>")
					.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">")
					.append("</head>")
					.append("<body bgcolor=\"#5CACE5\" text=\"#f0f8ff\" link=\"#f0f8ff\" alink=\"#ffd700\">");
		} else {
			header.append("<html>")
					.append("<head>")
					.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">")
					.append("<meta SYABAS-FULLSCREEN>")
					.append("</head>")
					.append("<body bgcolor=\"#5CACE5\" text=\"#f0f8ff\" link=\"#f0f8ff\" alink=\"#ffd700\">");
		}
		return header.toString();
	}

	private String getHtmlFooter() {
		StringBuilder footer = new StringBuilder();
		footer.append("</body>").append("</html>");
		return footer.toString();

	}

	private String getCategories(HTMLTYPE type) {
		StringBuilder response = new StringBuilder();

		if (type == HTMLTYPE.NORMAL) {
			response.append("<a href=\"./list\">Todos</a><br>");
			response.append("<a href=\"./estrenos\">Estrenos</a><br>");
			response.append("<a href=\"./peliculas\">Peliculas</a><br>");
			response.append("<a href=\"./hdrip\">HDRIP</a><br>");
			response.append("<a href=\"./microhd\">MicroHD</a><br>");
			response.append("<a href=\"./series\">Series</a><br>");
			response.append("<a href=\"./docusytv\">Documentales y TV</a><br>");
			response.append("<a href=\"./search?banshee\">Banshee</a><br>");
			response.append("<a href=\"./search?big+bang\">Big Bang Theory</a><br>");
		} else {
			response.append("<a href=\"./listpch\">Todos</a><br>");
			response.append("<a href=\"./estrenospch\">Estrenos</a><br>");
			response.append("<a href=\"./peliculaspch\">Peliculas</a><br>");
			response.append("<a href=\"./hdrippch\">HDRIP</a><br>");
			response.append("<a href=\"./microhdpch\">MicroHD</a><br>");
			response.append("<a href=\"./seriespch\">Series</a><br>");
			response.append("<a href=\"./docusytvpch\">Documentales y TV</a><br>");
			response.append("<a href=\"./searchpch?banshee\">Banshee</a><br>");
			response.append("<a href=\"./searchpch?big+bang+theory\">Big Bang Theory</a><br>");
		}

		response.append("<br>");

		return response.toString();
	}

	private String prepareList(List<Ficha> fichas, HTMLTYPE type) {
		StringBuilder response = new StringBuilder();
		int maxColumns = 8;

		if (type == HTMLTYPE.POPCORNHOUR) {
			maxColumns = 3;
		}

		response.append(getHtmlHeader(type));

		if (runPeerflix.isRunning()) {
			response.append("<a href=\"http://").append(getInternalIP())
					.append(":1234\"");
			if (type == HTMLTYPE.POPCORNHOUR) {
				response.append(" vod");
			}
			response.append(">Ver ").append(runPeerflix.getFicha().getNombre())
					.append("</a><br><br>");
		}

		response.append("<a href=\"./next\">Siguiente</a> | ");
		response.append(numPage);
		response.append(" | <a href=\"./back\">Anterior</a><br><br>");
		response.append(getCategories(type));
	
		if (fichas != null) {
			response.append("<table>");
			int col = 0;
			int i = 0;
			for (Ficha ficha : fichas) {
				if (ficha != null) {
					if (col == 0) {
						response.append("<tr>");
					}
					if (type == HTMLTYPE.POPCORNHOUR) {
						response.append("<td>").append("<a href=\"./torrent?")
								.append(i).append("\">").append(ficha.getNombre())
								.append("</a>").append("<td>");
					} else {
						response.append("<td><img src=\"")
								.append(ficha.getImagen())
								.append("\" border=\"0\"><br>")
								.append("<a href=\"./torrent?").append(i)
								.append("\">").append(ficha.getNombre())
								.append("</a>").append("<td>");
					}
					col++;
					i++;
					if (col == maxColumns) {
						response.append("</tr>");
						col = 0;
					}
				}
			}
			response.append("</table>");
		}
		response.append(getHtmlFooter());

		return response.toString();
	}
	
	private String tryNext() {
		numPage++;
		if (typePage == 10) {
			return trySearch();
		} else {
			return tryList();
		}
	}
	
	private String tryBack() {
		numPage--;
		if (numPage < 1) {
			numPage = 1;
		}
		if (typePage == 10) {
			return trySearch();
		} else {
			return tryList();
		}
	}
	
	private List<Ficha> getSelectedElementsByPage() {
		int firstElement = ((numPage - 1) * elementsPerPage) + 1;
		List<Ficha> selection = new ArrayList<Ficha>();
		
		for (int i=0; i<elementsPerPage; i++) {
			if (i+firstElement > fichas.size()) {
				break;
			}
			selection.add(fichas.get(firstElement+i-1));
		}
		
		return selection;
	}

}
