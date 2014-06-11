package servers;

import java.io.IOException;
import java.net.URI;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import servers.conf.ServerConfigurator;

public class ServerFrontEnd {
	public static URI BASE_URI;

	public static void main(String[] args) {
		ServerConfigurator.configure();

		BASE_URI = URI.create(ServerConfigurator.getBaseweburl());
		try {
			ResourceConfig rc = new ResourceConfig();
			rc.registerClasses();

			final HttpServer server = GrizzlyHttpServerFactory
					.createHttpServer(BASE_URI, rc);
			final ServerConfiguration config = server.getServerConfiguration();

			StaticHttpHandler staticHttpHandlerWeb = new StaticHttpHandler(
					ServerConfigurator.getWebpath());
			config.addHttpHandler(staticHttpHandlerWeb,
					ServerConfigurator.getWeburlpath());

			System.out.println(String
					.format("Application started.%nHit enter to stop it..."));
			System.in.read();
			server.shutdownNow();
		} catch (IOException ex) {
			Logger.getLogger(ServerBackEnd.class.getName()).log(Level.FATAL,
					null, ex);
		}
	}
}
