package servers;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import rest.api.WebResource;
import rest.model.MyObjectMapperProvider;

public class ServerHTTPS {
	private static final String KEYSTORE_SERVER_FILE = "keystore_server";
	private static final String KEYSTORE_SERVER_PWD = "kinakuta";
	public static final URI BASE_URI = URI.create("https://localhost:4330/api");
	public static void main(String[] args) {
		try {
			System.out.println("pContest API server");
			// Grizzly ssl configuration
			SSLContextConfigurator sslContext = new SSLContextConfigurator();
			// set up security context
			sslContext.setKeyStoreFile(KEYSTORE_SERVER_FILE);
			sslContext.setKeyStorePass(KEYSTORE_SERVER_PWD);

			ResourceConfig rc = new ResourceConfig();
			rc.registerClasses(WebResource.class, MyObjectMapperProvider.class,
					JacksonFeature.class, CorsSupportFilter.class);

			final HttpServer server = GrizzlyHttpServerFactory
					.createHttpServer(
							BASE_URI,
							rc,
							true,
							new SSLEngineConfigurator(sslContext)
									.setClientMode(false).setNeedClientAuth(
											false));

			System.out.println(String
					.format("Application started.%nHit enter to stop it..."));
			System.in.read();
			server.shutdownNow();
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName())
					.log(Level.SEVERE, null, ex);
		}

	}

	public static ResourceConfig createApp() {
		return new MyApplication();
	}

}
