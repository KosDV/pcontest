package servers;

import java.io.IOException;
import java.net.URI;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import rest.api.WebResource;
import rest.model.MyObjectMapperProvider;
import servers.conf.ServerConfigurator;

public class ServerBackEnd {
	public static URI BASE_URI;

	public static void main(String[] args) {
		ServerConfigurator.configure();

		BASE_URI = URI.create(ServerConfigurator.getBaseapiurl());
		try {
			ResourceConfig rc = new ResourceConfig();
			rc.registerClasses(WebResource.class, MyObjectMapperProvider.class,
					JacksonFeature.class, CorsSupportFilter.class);

			final HttpServer server = GrizzlyHttpServerFactory
					.createHttpServer(BASE_URI, rc);

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
