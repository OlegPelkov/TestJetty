import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;

public class SimpleServer {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8081);
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(servletContextHandler);
        MyServlet myServlet = new MyServlet();
        ServletHolder servletHolder = new ServletHolder(myServlet);
        servletContextHandler.addServlet(servletHolder,"/test");
        server.start();
        server.join();

    }
}
