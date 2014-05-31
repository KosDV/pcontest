package servers;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import rest.api.WebResource;
import rest.model.MyObjectMapperProvider;

public class MyApplication extends ResourceConfig {
	public MyApplication() {
		super(WebResource.class,
		// register Jackson ObjectMapper resolver
				MyObjectMapperProvider.class, JacksonFeature.class, CorsSupportFilter.class);
	}
}