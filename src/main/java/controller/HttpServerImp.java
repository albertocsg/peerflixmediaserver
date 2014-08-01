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

		System.out.println(exchange.getRequestURI().getPath());
		System.out.println(exchange.getRequestURI().getQuery());

		OutputStream out = null;
		String response = null;

		if (exchange.getRequestURI().getPath().equals("/list")) {
			response = tryList();
		} else if (exchange.getRequestURI().getPath().equals("/detail")) {
			response = tryDetail();
		} else if (exchange.getRequestURI().getPath().equals("/torrent")) {
			response = tryTorrent(exchange.getRequestURI().getQuery());
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

		fichas = new ArrayList<Ficha>();
		for (int i = 1; i < 5; i++) {
			String page = documentsOper.getPageURL(i);
			List<Ficha> fichasTmp = documentsOper.processPage(page);
			fichas.addAll(fichasTmp);
		}

		System.out.println(fichas.size());

		int col = 0;
		int i = 0;
		response.append("<table>");
		for (Ficha ficha : fichas) {
			if (col == 0) {
				response.append("<tr>");
			}
			response.append("<td>")
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

	private String tryDetail() {
		return "detail";
	}

	private String tryTorrent(String index) {
		String response = "";
		int i = Integer.valueOf(index);
		if (fichas.get(i).getTorrent() == null) {
			String torrent = documentsOper.getTorrent(fichas.get(i).getUrl());
			fichas.get(i).setTorrent(torrent);
			System.out.println("torrent: " + torrent);
		}
		runPeerflix.stop();
		runPeerflix.run(fichas.get(i).getTorrent());

		response = "<a href=\"http://" + getInternalIP()
				+ ":1234\" vod>ver</a>";

		return response;
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

}
