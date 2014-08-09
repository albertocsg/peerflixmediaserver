package view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import controller.IHttpServer;

public class PeerFlixServer {

	public static void main(String[] args) {
		
		ApplicationContext context =
				new ClassPathXmlApplicationContext("SpringBeans.xml");

		IHttpServer httpServerOper = (IHttpServer) context.getBean("httpServer");
		httpServerOper.init();
		
		while (true) {
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
