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

	/**
	 * Constructor
	 */
	public HttpServerImp() {
	}

	public void init() {
		try {
			
			String httpPort = config.getValue(Keys.PORT);

			httpServer = HttpServer.create(new InetSocketAddress(Integer.valueOf(httpPort).intValue()), 0);
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
		String search = null;

		if (exchange.getRequestURI().getPath().equals("/list")) {
			response = tryList(0);
		} else if (exchange.getRequestURI().getPath().equals("/estrenos")) {
			response = tryList(1);
		} else if (exchange.getRequestURI().getPath().equals("/peliculas")) {
			response = tryList(2);
		} else if (exchange.getRequestURI().getPath().equals("/hdrip")) {
			response = tryList(3);
		} else if (exchange.getRequestURI().getPath().equals("/microhd")) {
			response = tryList(4);
		} else if (exchange.getRequestURI().getPath().equals("/series")) {
			response = tryList(5);
		} else if (exchange.getRequestURI().getPath().equals("/docusytv")) {
			response = tryList(6);
		} else if (exchange.getRequestURI().getPath().equals("/search")) {
			search = exchange.getRequestURI().getQuery();
			response = trySearch(search);
		} else if (exchange.getRequestURI().getPath().equals("/listpch")) {
			response = tryListPCH(0);
		} else if (exchange.getRequestURI().getPath().equals("/estrenospch")) {
			response = tryListPCH(1);
		} else if (exchange.getRequestURI().getPath().equals("/peliculaspch")) {
			response = tryListPCH(2);
		} else if (exchange.getRequestURI().getPath().equals("/hdrippch")) {
			response = tryListPCH(3);
		} else if (exchange.getRequestURI().getPath().equals("/microhdpch")) {
			response = tryListPCH(4);
		} else if (exchange.getRequestURI().getPath().equals("/seriespch")) {
			response = tryListPCH(5);
		} else if (exchange.getRequestURI().getPath().equals("/docusytvpch")) {
			response = tryListPCH(6);
		} else if (exchange.getRequestURI().getPath().equals("/searchpch")) {
			search = exchange.getRequestURI().getQuery();
			response = trySearchPCH(search);
		} else if (exchange.getRequestURI().getPath().equals("/detail")) {
			response = tryDetail();
		} else if (exchange.getRequestURI().getPath().equals("/torrent")) {
			response = tryTorrent(exchange.getRequestURI().getQuery());
		} else if (exchange.getRequestURI().getPath().equals("/test")) {
			response = testBootstrap();
		} else {
			response = "Página no encontrada...";
		}

		exchange.sendResponseHeaders(200, response.length());

		out = exchange.getResponseBody();
		out.write(response.getBytes(), 0, response.length());
		out.flush();

		out.close();
	}

	private String tryList(int type) {
		StringBuilder response = new StringBuilder();

		fichas = new ArrayList<Ficha>();
		for (int i = 1; i <= 5; i++) {
			String page = documentsOper.getPageURL(type,i);
			List<Ficha> fichasTmp = documentsOper.processPage(page);
			fichas.addAll(fichasTmp);
		}

		if (runPeerflix.isRunning()) {
			response.append("<a href=\"http://").append(getInternalIP())
					.append(":1234\" vod>Ver ")
					.append(runPeerflix.getFicha().getNombre())
					.append("</a><br><br>");
		}

		response.append(getCategories());
		
		response.append("<table>");
		int col = 0;
		int i = 0;
		for (Ficha ficha : fichas) {
			if (col == 0) {
				response.append("<tr>");
			}
			response.append("<td><img src=\"").append(ficha.getImagen())
					.append("\" border=\"0\"><br>")
					.append("<a href=\"./torrent?").append(i).append("\">")
					.append(ficha.getNombre()).append("</a>").append("<td>");
			col++;
			i++;
			if (col == 8) {
				response.append("</tr>");
				col = 0;
			}
		}
		response.append("</table>");

		return response.toString();
	}
	
	private String trySearch(String search) {
		StringBuilder response = new StringBuilder();

		fichas = new ArrayList<Ficha>();
		for (int i = 1; i <= 5; i++) {
			String page = documentsOper.getPageSearch(search, i);
			List<Ficha> fichasTmp = documentsOper.processPage(page);
			fichas.addAll(fichasTmp);
		}

		if (runPeerflix.isRunning()) {
			response.append("<a href=\"http://").append(getInternalIP())
					.append(":1234\" vod>Ver ")
					.append(runPeerflix.getFicha().getNombre())
					.append("</a><br><br>");
		}

		response.append(getCategories());
		
		response.append("<table>");
		int col = 0;
		int i = 0;
		for (Ficha ficha : fichas) {
			if (ficha != null) {
				if (col == 0) {
					response.append("<tr>");
				}
				response.append("<td><img src=\"").append(ficha.getImagen())
						.append("\" border=\"0\"><br>")
						.append("<a href=\"./torrent?").append(i).append("\">")
						.append(ficha.getNombre()).append("</a>").append("<td>");
				col++;
				i++;
				if (col == 8) {
					response.append("</tr>");
					col = 0;
				}
			}
		}
		response.append("</table>");

		return response.toString();
	}
	
	private String tryListPCH(int type) {
		StringBuilder response = new StringBuilder();

		fichas = new ArrayList<Ficha>();
		for (int i = 1; i <= 5; i++) {
			String page = documentsOper.getPageURL(type,i);
			List<Ficha> fichasTmp = documentsOper.processPage(page);
			fichas.addAll(fichasTmp);
		}

		response.append(getHtmlHeader());
		if (runPeerflix.isRunning()) {
			response.append("<a href=\"http://").append(getInternalIP())
					.append(":1234\" vod>Ver ")
					.append(runPeerflix.getFicha().getNombre())
					.append("</a><br><br>");
		}
		response.append(getCategoriesPCH());
		response.append("<table>");
		int col = 0;
		int i = 0;
		for (Ficha ficha : fichas) {
			if (ficha != null) {
				if (col == 0) {
					response.append("<tr>");
				}
				response.append("<td>")
						.append("<a href=\"./torrent?").append(i).append("\">")
						.append(ficha.getNombre()).append("</a>").append("<td>");
				col++;
				i++;
				if (col == 3) {
					response.append("</tr>");
					col = 0;
				}
			}
		}
		response.append("</table>");
		response.append(getHtmlFooter());

		return response.toString();
	}

	private String trySearchPCH(String search) {
		StringBuilder response = new StringBuilder();

		fichas = new ArrayList<Ficha>();
		for (int i = 1; i <= 5; i++) {
			String page = documentsOper.getPageSearch(search, i);
			List<Ficha> fichasTmp = documentsOper.processPage(page);
			fichas.addAll(fichasTmp);
		}

		response.append(getHtmlHeader());
		if (runPeerflix.isRunning()) {
			response.append("<a href=\"http://").append(getInternalIP())
					.append(":1234\" vod>Ver ")
					.append(runPeerflix.getFicha().getNombre())
					.append("</a><br><br>");
		}
		response.append(getCategoriesPCH());
		response.append("<table>");
		int col = 0;
		int i = 0;
		for (Ficha ficha : fichas) {
			if (ficha != null) {
				if (col == 0) {
					response.append("<tr>");
				}
				response.append("<td>")
						.append("<a href=\"./torrent?").append(i).append("\">")
						.append(ficha.getNombre()).append("</a>").append("<td>");
				col++;
				i++;
				if (col == 3) {
					response.append("</tr>");
					col = 0;
				}
			}
		}
		response.append("</table>");
		response.append(getHtmlFooter());

		return response.toString();
	}
	
	private String tryDetail() {
		return "detail";
	}

	private String tryTorrent(String index) {
		StringBuilder response = new StringBuilder();
		int i = Integer.valueOf(index);
		if (fichas.get(i).getTorrent() == null) {
			documentsOper.getTorrent(fichas.get(i));
		}
		runPeerflix.run(fichas.get(i));

		response.append("<table><tr>");
		response.append("<td><img src=\"")
			.append(fichas.get(i).getImagen())
			.append("\" border=\"0\"></td>");
		response.append("<td>")
			.append(fichas.get(i).getDetails())
			.append("</td></tr></table>");
		response.append("<br><br><a href=\"http://")
			.append(getInternalIP())
			.append(":1234\" vod>Ver ").append(fichas.get(i).getNombre()).append("</a>");

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
		header.append("<html>")
			.append("<head>")
			.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">")
			.append("<meta SYABAS-FULLSCREEN>")
			.append("</head>")
			.append("<body bgcolor=\"#111111\" text=\"#f0f8ff\" link=\"#f0f8ff\" alink=\"#ffd700\">");
		return header.toString();
	}
	
	private String getHtmlFooter() {
		StringBuilder footer = new StringBuilder();
		footer.append("</body>")
			.append("</html>");
		return footer.toString();

	}
	
	private String getCategoriesPCH() {
		StringBuilder response = new StringBuilder();
		
		response.append("<a href=\"./test\">Test Bootstrap</a><br>");
		response.append("<a href=\"./listpch\">Todos</a><br>");
		response.append("<a href=\"./estrenospch\">Estrenos</a><br>");
		response.append("<a href=\"./peliculaspch\">Peliculas</a><br>");
		response.append("<a href=\"./hdrippch\">HDRIP</a><br>");
		response.append("<a href=\"./microhdpch\">MicroHD</a><br>");
		response.append("<a href=\"./seriespch\">Series</a><br>");
		response.append("<a href=\"./docusytvpch\">Documentales y TV</a><br>");
		response.append("<a href=\"./searchpch?banshee\">Banshee</a><br>");
		response.append("<a href=\"./searchpch?big+bang\">Big Bang Theory</a><br>");
		response.append("<br>");
		
		return response.toString();
	}

	private String getCategories() {
		StringBuilder response = new StringBuilder();
		
		response.append("<a href=\"./test\">Test Bootstrap</a><br>");
		response.append("<a href=\"./list\">Todos</a><br>");
		response.append("<a href=\"./estrenos\">Estrenos</a><br>");
		response.append("<a href=\"./peliculas\">Peliculas</a><br>");
		response.append("<a href=\"./hdrip\">HDRIP</a><br>");
		response.append("<a href=\"./microhd\">MicroHD</a><br>");
		response.append("<a href=\"./series\">Series</a><br>");
		response.append("<a href=\"./docusytv\">Documentales y TV</a><br>");
		response.append("<a href=\"./search?banshee\">Banshee</a><br>");
		response.append("<a href=\"./search?big+bang\">Big Bang Theory</a><br>");
		response.append("<br>");
		
		return response.toString();
	}
	
	private String testBootstrap() {
		StringBuilder page = new StringBuilder();

		page.append("	<!DOCTYPE html>");
		page.append("		<html lang=\"en\">");
		page.append("		  <head>");
		page.append("		    <meta charset=\"utf-8\">");
		page.append("		    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		page.append("		    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		page.append("		    <title>Starter Template for Bootstrap</title>");
		page.append("		    <!-- Bootstrap core CSS -->");
		page.append("		    <link href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css\" rel=\"stylesheet\">");
		page.append("		  </head>");
		page.append("		  <body>");
		page.append("		    <div class=\"navbar navbar-inverse navbar-fixed-top\" role=\"navigation\">");
		page.append("		      <div class=\"container\">");
		page.append("		        <div class=\"navbar-header\">");
		page.append("		          <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-collapse\">");
		page.append("		            <span class=\"sr-only\">Toggle navigation</span>");
		page.append("		            <span class=\"icon-bar\"></span>");
		page.append("		            <span class=\"icon-bar\"></span>");
		page.append("		            <span class=\"icon-bar\"></span>");
		page.append("		          </button>");
		page.append("		          <a class=\"navbar-brand\" href=\"#\">Project name</a>");
		page.append("		        </div>");
		page.append("		        <div class=\"collapse navbar-collapse\">");
		page.append("		          <ul class=\"nav navbar-nav\">");
		page.append("		            <li class=\"active\"><a href=\"#\">Home</a></li>");
		page.append("		            <li><a href=\"#about\">About</a></li>");
		page.append("		            <li><a href=\"#contact\">Contact</a></li>");
		page.append("		          </ul>");
		page.append("		        </div><!--/.nav-collapse -->");
		page.append("		      </div>");
		page.append("		    </div>");
		page.append("		    <div class=\"container\">");
		page.append("		      <div class=\"starter-template\">");
		page.append("		        <h1>Bootstrap starter template</h1>");
		page.append("		        <p class=\"lead\">Use this document as a way to quickly start any new project.<br> All you get is this text and a mostly barebones HTML document.</p>");
		page.append("		      </div>");
		page.append("		    </div><!-- /.container -->");
		page.append("		  </body>");
		page.append("		</html>");
		
		
		return page.toString(); 
	}

}
