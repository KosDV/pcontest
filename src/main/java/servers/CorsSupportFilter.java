package servers;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

public class CorsSupportFilter implements ContainerResponseFilter {

    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        headers.add("Access-Control-Allow-Origin", "*");
        // headers.add("Access-Control-Allow-Origin", "http://pcontest.com");
        // //allows CORS requests only coming from pcontest.com
        headers.add("Access-Control-Allow-Methods",
                "GET, POST, DELETE, PUT, OPTIONS");
        headers.add("Access-Control-Max-Age", "1000");
        headers.add("Access-Control-Allow-Headers",
                "X-Requested-With, Content-Type, Accept, Origin");
    }
}
