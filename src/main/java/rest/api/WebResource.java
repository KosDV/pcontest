package rest.api;

import hibernate.manager.UserManager;
import hibernate.model.Photo;
import hibernate.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;

import rest.model.StatusDTO;
import rest.model.UserDTO;
import rest.model.RegisterDTO;

@Path("/web")
public class WebResource {

	public UserManager mgmtUser;
	Integer OK = 200;
	Integer BAD_REQUEST = 400;
	Integer UNAUTHORIZED = 401;
	Integer INTERNAL_ERROR = 500;
	Integer CONTEST_OPENED = 600;
	Integer PRESENTATIONS_OPENED = 601;
	Integer VOTES_OPENED = 602;
	Integer CONTEST_CLOSED = 603;

	@GET
	@Path("/users/test")
	public Response getTest() {
		return Response.status(200).entity("OK!").build();
	}

	@GET
	@Path("/users/list")
	private List<User> getUsersTest() {
		List<User> list = new ArrayList<User>();
		list = mgmtUser.loadAllUsers();
		return list;
	}

	@POST
	@Path("/users/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusDTO registerResource(RegisterDTO user) {
		// TODO check contest status
		if (user == null)
			return new StatusDTO(BAD_REQUEST, "Please, insert an user", "TODO");
		else {
			Queries query = new Queries();
			try {
				if (query.checkUserExist(user.getNif()) == true)
					return new StatusDTO(BAD_REQUEST,
							"Someone already has that nif. Try another?",
							"TODO");
				query.registerUser(user);
				return new StatusDTO(OK, "User registered correctly", "TODO");
			} catch (HibernateException e) {
				System.err.println(e.getMessage());
				return new StatusDTO(BAD_REQUEST, "Please check parameters",
						"TODO");
			} catch (NoSuchAlgorithmException e) {
				System.err.println(e.getMessage());
				return new StatusDTO(INTERNAL_ERROR,
						"Ups, something was wrong", "TODO");
			}
		}
	}

	@GET
	@Path("/users/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusDTO loginResource(@QueryParam("nif") String nif,
			@QueryParam("pass") String password) {
		Queries query = new Queries();
		// TODO check contest status
		try {
			if (query.checkUserExist(nif) == true) {
				if (query.checkUserPassword(nif, password) == false)
					return new StatusDTO(BAD_REQUEST,
							"The nif or password you entered is incorrect.",
							"TODO");
				return new StatusDTO(OK, "User signed in successfully.", "TODO");
			}
			return new StatusDTO(BAD_REQUEST,
					"The nif or password you entered is incorrect.", "TODO");

		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
			return new StatusDTO(INTERNAL_ERROR, "Ups, something was wrong",
					"TODO");
		}
	}

	@GET
	@Path("/users/{user-code}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserDTO getUserResource(@PathParam("user-code") String userNif) {
		UserManager userManager = new UserManager();
		User user = userManager.findByUserNif(userNif);

		if (user != null) {
			return new UserDTO(user);
		}
		return null;

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
	public Response uploadPhotoResource(Photo image,
			@DefaultValue("null") @QueryParam("user") String userId) {
		if (image == null || userId.isEmpty())
			return Response.status(400).entity("KO!").build();
		return Response.status(200).entity("OK!").build();
	}

	@GET
	@Path("/photos")
	@Produces(MediaType.APPLICATION_JSON)
	public HashSet<Photo> getPhotosToVoteResource(
			@DefaultValue("null") @QueryParam("user") String userId) {
		HashSet<Photo> photos = null;
		return photos;
	}

}
