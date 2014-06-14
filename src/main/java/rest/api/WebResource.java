package rest.api;

import hibernate.manager.UserManager;
import hibernate.model.Photo;
import hibernate.model.User;

import java.io.BufferedInputStream;
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

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

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
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public StatusDTO uploadPhotoResource(@QueryParam("nif") String nif,
	    @QueryParam("pass") String password,
	    @FormDataParam("file") InputStream photoS,
	    @FormDataParam("file") FormDataContentDisposition photoDetail,
	    @FormDataParam("title") String title,
	    @FormDataParam("description") String description) {
	String filename = photoDetail.getFileName();
	Integer fileSize = (int) photoDetail.getSize();

	Queries query = new Queries();
	Integer contestStatus = query.checkContestStatus();
	try {
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

	    if (photoS == null)
		return new StatusDTO(Status.BAD_REQUEST,
			"Please, upload a photo", contestStatus);
	    // TODO check that file is an image jpeg, check size

	    else if (photoDetail.getFileName() == null
		    || photoDetail.getFileName().length() < 4)
		return new StatusDTO(Status.FILENAME_REQUIRED,
			"There is a problem with the filename!", contestStatus);
	    else if (title == null || title.isEmpty())
		return new StatusDTO(Status.TITLE_REQUIRED,
			"Please, insert a title", contestStatus);
	    else if (description == null || description.isEmpty())
		return new StatusDTO(Status.DESCRIPTION_REQUIRED,
			"Please, insert a description", contestStatus);
	    // TODO obtain metadata, check that image is already in the server
	    System.out.println("photo size: " + fileSize);
	    System.out.println("photo type: " + photoDetail.getType());
	    System.out.println("photo fileName: " + filename);
	    System.out.println("photo name: " + photoDetail.getName());

	    Random randomGenerator = new Random();
	    Integer salt = randomGenerator.nextInt();
	    StringBuilder pathRand = new StringBuilder(filename).append(salt);
	    String pathDigested = Digest.generateSHA2(pathRand.toString());

	    StringBuilder fileLocationBuilder = new StringBuilder(
		    ServerConfigurator.getPhotopath());
	    fileLocationBuilder.append(pathDigested).append("/");
	    String fileLocation = fileLocationBuilder.toString();
	    System.out.println("FILEPATH_DIGESTED: " + fileLocation);

	    try {
		PhotoUtil.writeToFile(photoS, fileLocation, fileSize);
	    } catch (IOException e) {
		return new StatusDTO(Status.PHOTO_NOT_UPLOADED,
			"This file cannot be uploaded", contestStatus);
	    }

	    StringBuilder urlBuilder = new StringBuilder(
		    ServerConfigurator.getBaseapiurl());
	    urlBuilder.append(ServerConfigurator.getPhotourlpath()).append("/")
		    .append(fileLocation);
	    String imageURL = urlBuilder.toString();
	    System.out.println("IMAGEURL: " + imageURL);

	    try {
		Metadata metadata = ImageMetadataReader.readMetadata(
			(BufferedInputStream) photoS, false);
		for (Directory directory : metadata.getDirectories()) {
		    for (Tag tag : directory.getTags()) {
			System.out.println(tag);
		    }
		}

	    } catch (ImageProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	    Photo photo = new Photo();
	    photo.setTitle(title);
	    photo.setDescription(description);
	    photo.setFilename(filename);
	    // TODO add DATE
	    photo.setSalt(salt);
	    photo.setUrl(imageURL);
	    // TODO add metadata

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
		return new StatusDTO(Status.USER_DONT_UPLOADED_PHOTO,
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
