package view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import controller.IHttpServer;

public class PeerFlixServer {

	private static ApplicationContext context;

	public static void main(String[] args) {

		context = new ClassPathXmlApplicationContext("SpringBeans.xml");

		IHttpServer httpServerOper = (IHttpServer) context
				.getBean("httpServer");
		httpServerOper.init();

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
