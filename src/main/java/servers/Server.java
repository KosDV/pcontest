package servers;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Server {
	// Base URI the Grizzly HTTP server will listen on
	//public static final URI BASE_URI = URI.create("https://localhost:80433/api");

	public static final URI BASE_URI = URI.create("http://localhost:8000/api");

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
	 * application.
	 * 
	 * @return Grizzly HTTP server.
	 */

	@SuppressWarnings({ "ResultOfMethodCallIgnored" })
	public static void main(String[] args) {
		try {
			System.out.println("JSON with Jackson Jersey Example App");

			final HttpServer server = GrizzlyHttpServerFactory
					.createHttpServer(BASE_URI, createApp());

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
