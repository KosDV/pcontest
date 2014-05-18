package rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/web")
public class WebResource {
    @GET
    @Path("/users/test")
    public Response getTest() {
        String msg = "OK!";

        return Response.status(200).entity(msg).build();
    }
}
