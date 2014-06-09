package servers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ReadHandler;
import org.glassfish.grizzly.http.io.NIOInputStream;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.http.util.HttpStatus;
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

			StaticHttpHandler staticHttpHandlerPhotos = new StaticHttpHandler(
					ServerConfigurator.getPhotopath());
			config.addHttpHandler(staticHttpHandlerPhotos,
					ServerConfigurator.getPhotourlpath());

			// Map the path, PhotoUrlPath, to the NonBlockingUploadHandler
			config.addHttpHandler(new NonBlockingUploadHandler(),
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

	/**
	 * This handler using non-blocking streams to read POST data and store it to
	 * the local file.
	 */
	private static class NonBlockingUploadHandler extends HttpHandler {

		private final AtomicInteger counter = new AtomicInteger();

		@Override
		public void service(final Request request, final Response response)
				throws Exception {

			final NIOInputStream in = (NIOInputStream) request.getInputStream();

			final FileChannel fileChannel = new FileOutputStream("./"
					+ counter.incrementAndGet()
					+ ServerConfigurator.getPhotourlnio()).getChannel();

			response.suspend();

			// If we don't have more data to read - onAllDataRead() will be
			// called
			in.notifyAvailable(new ReadHandler() {

				public void onDataAvailable() throws Exception {
					Logger.getLogger("[onDataAvailable] length: {0}");
					storeAvailableData(in, fileChannel);
					in.notifyAvailable(this);
				}

				public void onError(Throwable t) {
					Logger.getLogger("[onError]");
					response.setStatus(500, t.getMessage());
					complete(true);

					if (response.isSuspended()) {
						response.resume();
					} else {
						response.finish();
					}
				}

				public void onAllDataRead() throws Exception {
					Logger.getLogger("[onAllDataRead] length: {0}");
					storeAvailableData(in, fileChannel);
					response.setStatus(HttpStatus.ACCEPTED_202);
					complete(false);
					response.resume();
				}

				private void complete(final boolean isError) {
					try {
						fileChannel.close();
					} catch (IOException e) {
						if (!isError) {
							response.setStatus(500, e.getMessage());
						}
					}

					try {
						in.close();
					} catch (IOException e) {
						if (!isError) {
							response.setStatus(500, e.getMessage());
						}
					}
				}
			});
		}

		private static void storeAvailableData(NIOInputStream in,
				FileChannel fileChannel) throws IOException {
			// Get the Buffer directly from NIOInputStream
			final Buffer buffer = in.readBuffer();
			// Retrieve ByteBuffer
			final ByteBuffer byteBuffer = buffer.toByteBuffer();

			try {
				while (byteBuffer.hasRemaining()) {
					// Write the ByteBuffer content to the file
					fileChannel.write(byteBuffer);
				}
			} finally {
				// we can try to dispose the buffer
				buffer.tryDispose();
			}
		}

	} // END NonBlockingUploadHandler
}
