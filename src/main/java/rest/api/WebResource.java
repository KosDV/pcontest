package rest.api;

import hibernate.model.Image;
import hibernate.model.User;

import java.util.HashSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/web")
public class WebResource {

	@GET
	@Path("/users/test")
	public Response getTest() {

		return Response.status(200).entity("OK!").build();
	}

	@POST
	@Path("/users/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerResource(User user) {
		if (user == null)
			return Response.status(400).entity("KO!").build();
		return Response.status(201).entity("OK!").build();
	}

	@POST
	@Path("/users/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginResource(User user) {
		if (user == null)
			return Response.status(400).entity("KO!").build();
		return Response.status(200).entity("OK!").build();
	}

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public HashSet<User> getUsersResource() {
		HashSet<User> users = null;
		return users;
	}

	@POST
	@Path("/photos/upload")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadPhotoResource(Image image,
			@DefaultValue("null") @QueryParam("user") String userId) {
		if (image == null || userId.isEmpty())
			return Response.status(400).entity("KO!").build();
		return Response.status(200).entity("OK!").build();
	}

	@GET
	@Path("/photos")
	@Produces(MediaType.APPLICATION_JSON)
	public HashSet<Image> getPhotosToVoteResource(
			@DefaultValue("null") @QueryParam("user") String userId) {
		HashSet<Image> photos = null;
		return photos;
	}

}
