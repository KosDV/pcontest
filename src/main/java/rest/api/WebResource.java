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

import org.hibernate.HibernateException;

import rest.model.PhotoDTO;
import rest.model.RegisterDTO;
import rest.model.StatusDTO;
import rest.model.UserDTO;

@Path("/web")
public class WebResource {

	@GET
	@Path("/users/list")
	private List<User> getUsersTest() {
		UserManager mgmtUser = new UserManager();
		List<User> list = new ArrayList<User>();
		list = mgmtUser.loadAllUsers();
		return list;
	}

	@POST
	@Path("/users/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusDTO registerResource(RegisterDTO user) {
		Queries query = new Queries();
		Integer contestStatus = query.checkContestStatus();
		if (user == null)
			return new StatusDTO(Status.BAD_REQUEST, "Please, insert an user",
					contestStatus);
		else {
			try {
				if (query.checkUserExist(user.getNif()) == true)
					return new StatusDTO(Status.BAD_REQUEST,
							"Someone already has that nif. Try another?",
							contestStatus);
				query.registerUser(user);
				return new StatusDTO(Status.OK, "User registered correctly",
						contestStatus);
			} catch (HibernateException e) {
				System.err.println(e.getMessage());
				return new StatusDTO(Status.BAD_REQUEST,
						"Please check parameters", contestStatus);
			} catch (NoSuchAlgorithmException e) {
				System.err.println(e.getMessage());
				return new StatusDTO(Status.INTERNAL_ERROR,
						"Ups, something was wrong", contestStatus);
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
		Integer contestStatus = query.checkContestStatus();
		try {
			if (query.checkUserExist(nif) == false
					|| query.checkUserPassword(nif, password) == false)
				return new StatusDTO(Status.BAD_REQUEST,
						"The nif or password you entered is incorrect.",
						contestStatus);

			return new StatusDTO(Status.OK, "User signed in successfully.",
					contestStatus);

		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
			return new StatusDTO(Status.INTERNAL_ERROR,
					"Ups, something was wrong", contestStatus);
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

	@POST
	@Path("/photos/upload")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public StatusDTO uploadPhotoResource(@QueryParam("nif") String nif,
			@QueryParam("pass") String password, PhotoDTO photo) {
		Queries query = new Queries();
		Integer contestStatus = query.checkContestStatus();
		try {
			if (photo == null)
				return new StatusDTO(Status.BAD_REQUEST,
						"Please, upload a photo", contestStatus);
			else if (photo.getTitle() == null || photo.getTitle().isEmpty())
				return new StatusDTO(Status.TITLE_REQUIRED,
						"Please, insert a title", contestStatus);
			else if (photo.getDescription() == null
					|| photo.getDescription().isEmpty())
				return new StatusDTO(Status.DESCRIPTION_REQUIRED,
						"Please, insert a description", contestStatus);
			else if (photo.getFilename() == null
					|| photo.getFilename().isEmpty())
				return new StatusDTO(Status.FILENAME_REQUIRED,
						"There is a problem with the filename!", contestStatus);

			if (query.checkUserExist(nif) == false
					|| query.checkUserPassword(nif, password) == false)
				return new StatusDTO(Status.BAD_REQUEST,
						"The nif or password you entered is incorrect.",
						contestStatus);

			User user = query.getUser(nif);
			if (user.getImage() != null)
				return new StatusDTO(Status.USER_ALLREADY_HAS_IMAGE,
						"Forbidden! This user allready has an image.",
						contestStatus);

			query.insertPhoto(photo, user);
			return new StatusDTO(Status.OK, "Photo added successfully",
					contestStatus);
		} catch (HibernateException e) {
			System.err.println(e.getMessage());
			return new StatusDTO(Status.BAD_REQUEST, "Please check parameters",
					contestStatus);
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
			return new StatusDTO(Status.INTERNAL_ERROR,
					"Ups, something was wrong", contestStatus);
		}
	}

	@GET
	@Path("/photos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object getUserPhotoResource(@QueryParam("nif") String nif,
			@QueryParam("pass") String password) {
		Queries query = new Queries();
		Integer contestStatus = query.checkContestStatus();
		try {
			if (query.checkUserExist(nif) == false
					|| query.checkUserPassword(nif, password) == false)
				return new StatusDTO(Status.BAD_REQUEST,
						"The nif or password you entered is incorrect.",
						contestStatus);
			User user = query.getUser(nif);
			Photo photo = user.getImage();
			if (photo == null)
				return new StatusDTO(Status.USER_HAS_NOT_PHOTO,
						"This user has not uploaded a photo yet.",
						contestStatus);
			return new PhotoDTO(photo);

		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
			return new StatusDTO(Status.INTERNAL_ERROR,
					"Ups, something was wrong", contestStatus);
		}
	}

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public HashSet<User> getUsersResource() {
		HashSet<User> users = null;
		// TODO
		return users;
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
