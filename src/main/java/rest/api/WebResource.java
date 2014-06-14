package rest.api;

import hibernate.manager.UserManager;
import hibernate.model.Photo;
import hibernate.model.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.HibernateException;

import rest.model.PhotoDTO;
import rest.model.RegisterDTO;
import rest.model.StatusDTO;
import rest.model.UserDTO;
import servers.conf.ServerConfigurator;
import util.Digest;
import util.PhotoUtil;

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
	//if (contestStatus != Status.CONTEST_OPENED || contestStatus != Status.PRESENTATIONS_OPENED) {
	// return new StatusDTO(Status.BAD_REQUEST, "Registration is closed.",
	// contestStatus, false);
	// }
	if (user == null)
	    return new StatusDTO(Status.BAD_REQUEST, "Please, insert an user",
		    contestStatus, false, false);
	else {
	    try {
		if (query.checkUserExist(user.getNif()) == true)
		    return new StatusDTO(Status.BAD_REQUEST,
			    "Someone already has that nif. Try another?",
			    contestStatus, false, false);
		query.registerUser(user);
		return new StatusDTO(Status.OK, "User registered correctly",
			contestStatus, false, false);
	    } catch (HibernateException e) {
		System.err.println(e.getMessage());
		return new StatusDTO(Status.BAD_REQUEST,
			"Please check parameters", contestStatus, false, false);
	    } catch (NoSuchAlgorithmException e) {
		System.err.println(e.getMessage());
		return new StatusDTO(Status.INTERNAL_ERROR,
			"Ups, something was wrong", contestStatus, false, false);
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
			contestStatus, false, false);

	    return new StatusDTO(Status.OK, "User signed in successfully.",
		    contestStatus, query.checkUserHasPhotoByUserNif(nif), query.checkUserVotedByNif(nif));

	} catch (NoSuchAlgorithmException e) {
	    System.err.println(e.getMessage());
	    return new StatusDTO(Status.INTERNAL_ERROR,
		    "Ups, something was wrong", contestStatus, false, false);
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
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public StatusDTO uploadPhotoResource(@FormDataParam("nif") String nif,
	    @FormDataParam("pass") String password,
	    @FormDataParam("file") InputStream photoS,
	    @FormDataParam("file") FormDataContentDisposition photoDetail,
	    @FormDataParam("title") String title,
	    @FormDataParam("description") String description) {
	String filename = photoDetail.getFileName();
	Queries query = new Queries();
	Integer contestStatus = query.checkContestStatus();
	// if (contestStatus != Status.PRESENTATIONS_OPENED) {
	// return new StatusDTO(Status.BAD_REQUEST, "Presentations closed!.",
	// contestStatus, false);
	// }
	try {
	    if (query.checkUserExist(nif) == false
		    || query.checkUserPassword(nif, password) == false)
		return new StatusDTO(Status.BAD_REQUEST,
			"The nif or password you entered is incorrect.",
			contestStatus, false, false);

	    User user = query.getUser(nif);
	    Boolean photoStatus = query.checkUserHasPhoto(user);
	    Boolean userVoted = query.checkUserVoted(user);

	    if (user.getImage() != null)
		return new StatusDTO(Status.USER_ALLREADY_HAS_IMAGE,
			"Forbidden! This user allready has an image.",
			contestStatus, photoStatus, userVoted);

	    if (photoS == null)
		return new StatusDTO(Status.BAD_REQUEST,
			"Please, upload a photo", contestStatus, photoStatus, userVoted);
	    // TODO check that file is an image jpeg

	    else if (photoDetail.getFileName() == null
		    || photoDetail.getFileName().length() < 4)
		return new StatusDTO(Status.FILENAME_REQUIRED,
			"There is a problem with the filename!", contestStatus,
			photoStatus, userVoted);
	    else if (title == null || title.isEmpty())
		return new StatusDTO(Status.TITLE_REQUIRED,
			"Please, insert a title", contestStatus, photoStatus, userVoted);
	    else if (description == null || description.isEmpty())
		return new StatusDTO(Status.DESCRIPTION_REQUIRED,
			"Please, insert a description", contestStatus,
			photoStatus, userVoted);

	    Random randomGenerator = new Random();
	    Integer salt = randomGenerator.nextInt();
	    StringBuilder pathRand = new StringBuilder(filename).append(salt);
	    String pathDigested = Digest.generateSHA2(pathRand.toString());

	    StringBuilder pathLocationBuilder = new StringBuilder(
		    ServerConfigurator.getPhotopath());
	    pathLocationBuilder.append(pathDigested).append(File.separator);
	    String pathLocation = pathLocationBuilder.toString();
	    System.out.println("FILEPATH_DIGESTED: " + pathLocation);
	    File newDir = new File(pathLocation);
	    boolean succes = newDir.mkdir();
	    if (!succes) {
		return new StatusDTO(Status.PHOTO_NOT_UPLOADED,
			"This file cannot be uploaded", contestStatus,
			photoStatus, userVoted);
	    }

	    StringBuilder fileLocation = new StringBuilder(pathLocationBuilder);
	    fileLocation.append(filename);
	    File newFile = new File(fileLocation.toString());
	    try {
		PhotoUtil.writeToFile(photoS, newFile);
	    } catch (IOException e) {
		return new StatusDTO(Status.PHOTO_NOT_UPLOADED,
			"This file cannot be uploaded", contestStatus,
			photoStatus, userVoted);
	    }

	    StringBuilder urlBuilder = new StringBuilder(
		    ServerConfigurator.getBasephotourl());
	    urlBuilder.append(File.separator).append(pathDigested)
		    .append(File.separator).append(filename);
	    String imageURL = urlBuilder.toString();
	    System.out.println("IMAGEURL: " + imageURL);

	    // TODO get metadata from exif
	    Photo photo = new Photo();
	    photo.setTitle(title);
	    photo.setDescription(description);
	    photo.setFilename(filename);
	    // TODO add DATE
	    photo.setSalt(salt);
	    photo.setUrl(imageURL);
	    // TODO add metadata

	    query.insertPhoto(photo, user);
	    photoStatus = query.checkUserHasPhotoByUserNif(nif);
	    return new StatusDTO(Status.OK, "Photo added successfully",
		    contestStatus, photoStatus, userVoted);
	} catch (HibernateException e) {
	    System.err.println(e.getMessage());
	    return new StatusDTO(Status.BAD_REQUEST, "Please check parameters",
		    contestStatus, false, false);
	} catch (NoSuchAlgorithmException e) {
	    System.err.println(e.getMessage());
	    return new StatusDTO(Status.INTERNAL_ERROR,
		    "Ups, something was wrong", contestStatus, false, false);
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
			contestStatus, false, false);
	    User user = query.getUser(nif);
	    if (!query.checkUserHasPhoto(user))
		return new StatusDTO(Status.USER_DONT_UPLOADED_PHOTO,
			"This user has not uploaded a photo yet.",
			contestStatus, false, query.checkUserVoted(user));
	    return new PhotoDTO(user.getImage());

	} catch (NoSuchAlgorithmException e) {
	    System.err.println(e.getMessage());
	    return new StatusDTO(Status.INTERNAL_ERROR,
		    "Ups, something was wrong", contestStatus, false, false);
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
