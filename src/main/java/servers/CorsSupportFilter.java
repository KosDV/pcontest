package servers;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;


public class CorsSupportFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext ResponseContext) throws IOException {
        ResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        ResponseContext.getHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        //ResponseContext.getAllowedMethods().add("POST");
    }
}
