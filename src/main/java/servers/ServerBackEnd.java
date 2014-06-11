package servers;

import java.io.IOException;
import java.net.URI;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import rest.api.WebResource;
import rest.model.MyObjectMapperProvider;
import servers.conf.ServerConfigurator;

public class ServerBackEnd {
    private static URI BASE_URI;
    private static String KEYSTORE_SERVER_FILE;
    private static String KEYSTORE_SERVER_PWD;

    public static void main(String[] args) {
	ServerConfigurator.configure();
	BASE_URI = URI.create(ServerConfigurator.getBaseapiurl());
	KEYSTORE_SERVER_FILE = ServerConfigurator.getKeystoreServer();
	KEYSTORE_SERVER_PWD = ServerConfigurator.getKeystorepassword();

	try {
	    SSLContextConfigurator sslContext = new SSLContextConfigurator();
	    sslContext.setKeyStoreFile(KEYSTORE_SERVER_FILE);
	    sslContext.setKeyStorePass(KEYSTORE_SERVER_PWD);

	    ResourceConfig rc = new ResourceConfig();
	    rc.registerClasses(WebResource.class, MultiPartFeature.class,
		    MyObjectMapperProvider.class, JacksonFeature.class,
		    CorsSupportFilter.class);

	    final HttpServer server = GrizzlyHttpServerFactory
		    .createHttpServer(
			    BASE_URI,
			    rc,
			    true,
			    new SSLEngineConfigurator(sslContext)
			    .setClientMode(false).setNeedClientAuth(
				    false));

	    final ServerConfiguration config = server.getServerConfiguration();
	    StaticHttpHandler staticHttpHandlerPhotos = new StaticHttpHandler(
		    ServerConfigurator.getPhotopath());
	    config.addHttpHandler(staticHttpHandlerPhotos,
		    ServerConfigurator.getPhotourlpath());

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
