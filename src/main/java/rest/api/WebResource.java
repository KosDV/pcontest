package rest.api;

import hibernate.manager.UserManager;
import hibernate.model.Photo;
import hibernate.model.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.HibernateException;

import rest.model.ContestDTO;
import rest.model.PhotoDTO;
import rest.model.RegisterDTO;
import rest.model.StatusDTO;
import rest.model.UserDTO;
import rest.model.VoteDTO;
import servers.conf.ServerConfigurator;
import util.DigestUtil;
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
	if (contestStatus != Status.PRESENTATIONS_OPENED.intValue())
	    return new StatusDTO(Status.BAD_REQUEST, "Registration is closed.",
		    contestStatus, false, false);

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
	    if (!query.checkUserExist(nif)
		    || !query.checkUserPassword(nif, password))
		return new StatusDTO(Status.BAD_REQUEST,
			"The nif or password you entered are not correct",
			contestStatus, false, false);

	    return new StatusDTO(Status.OK, "User signed in successfully.",
		    contestStatus, query.checkUserHasPhoto(nif),
		    query.checkUserVoted(nif));

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
	if (contestStatus != Status.PRESENTATIONS_OPENED.intValue())
	    return new StatusDTO(Status.PRESENTATIONS_CLOSED,
		    "Presentations closed!.", contestStatus, false, false);

	try {
	    if (!query.checkUserExist(nif)
		    || !query.checkUserPassword(nif, password))
		return new StatusDTO(Status.BAD_REQUEST,
			"The nif or password you entered are not correct",
			contestStatus, false, false);

	    User user = query.getUser(nif);
	    Boolean userHasPhoto = query.checkUserHasPhoto(user);
	    Boolean userVoted = query.checkUserVoted(user);

	    if (userHasPhoto)
		return new StatusDTO(Status.USER_ALLREADY_HAS_IMAGE,
			"Forbidden! This user allready has an image.",
			contestStatus, userHasPhoto, userVoted);

	    if (photoS == null)
		return new StatusDTO(Status.BAD_REQUEST,
			"Please, upload a photo", contestStatus, userHasPhoto,
			userVoted);
	    // TODO check that file is an image jpeg

	    else if (photoDetail.getFileName() == null
		    || photoDetail.getFileName().length() < 4)
		return new StatusDTO(Status.FILENAME_REQUIRED,
			"There is a problem with the filename!", contestStatus,
			userHasPhoto, userVoted);
	    else if (title == null || title.isEmpty())
		return new StatusDTO(Status.TITLE_REQUIRED,
			"Please, insert a title", contestStatus, userHasPhoto,
			userVoted);
	    else if (description == null || description.isEmpty())
		return new StatusDTO(Status.DESCRIPTION_REQUIRED,
			"Please, insert a description", contestStatus,
			userHasPhoto, userVoted);

	    Random randomGenerator = new Random();
	    Integer salt = randomGenerator.nextInt();
	    StringBuilder pathRand = new StringBuilder(filename).append(salt);
	    String pathDigested = DigestUtil.generateSHA2(pathRand.toString());

	    StringBuilder pathLocationBuilder = new StringBuilder(
		    ServerConfigurator.getPhotopath());
	    pathLocationBuilder.append(pathDigested).append(File.separator);
	    String pathLocation = pathLocationBuilder.toString();
	    System.out.println("FILEPATH_DIGESTED: " + pathLocation);
	    File newDir = new File(pathLocation);
	    boolean succes = newDir.mkdir();
	    if (!succes) {
		return new StatusDTO(Status.PHOTO_CANNOT_BE_UPLOADED,
			"This file cannot be uploaded", contestStatus,
			userHasPhoto, userVoted);
	    }

	    StringBuilder fileLocation = new StringBuilder(pathLocationBuilder);
	    fileLocation.append(filename);
	    File newFile = new File(fileLocation.toString());
	    try {
		PhotoUtil.writeToFile(photoS, newFile);
	    } catch (IOException e) {
		return new StatusDTO(Status.PHOTO_CANNOT_BE_UPLOADED,
			"This file cannot be uploaded", contestStatus,
			userHasPhoto, userVoted);
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
	    userHasPhoto = query.checkUserHasPhoto(nif);
	    return new StatusDTO(Status.OK, "Photo added successfully",
		    contestStatus, userHasPhoto, userVoted);
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
    @Path("/photos/vote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object PhotosToVoteResource(@QueryParam("nif") String nif,
	    @QueryParam("pass") String password) {

	Queries query = new Queries();
	Integer contestStatus = query.checkContestStatus();
	if (contestStatus != Status.VOTES_OPENED.intValue())
	    return new StatusDTO(Status.VOTES_CLOSED, "Votes closed!.",
		    contestStatus, false, false);

	try {
	    if (query.checkUserExist(nif) == false
		    || query.checkUserPassword(nif, password) == false)
		return new StatusDTO(Status.BAD_REQUEST,
			"The nif or password you entered are not correct",
			contestStatus, false, false);

	    User user = query.getUser(nif);
	    Boolean userHasPhoto = query.checkUserHasPhoto(user);
	    Boolean userVoted = query.checkUserVoted(user);
	    if (!query.checkUserExist(nif)
		    || !query.checkUserPassword(nif, password))
		return new StatusDTO(Status.BAD_REQUEST,
			"The nif or password you entered are not correct",
			contestStatus, false, false);

	    if (!userHasPhoto)
		return new StatusDTO(Status.USER_HAS_NOT_UPLOADED_PHOTO,
			"Forbidden! This user has not upload an image.",
			contestStatus, userHasPhoto, userVoted);

	    if (userVoted)
		return new StatusDTO(Status.USER_HAS_ALREADY_VOTED,
			"Forbidden! This user has already voted.",
			contestStatus, userHasPhoto, userVoted);

	    List<PhotoDTO> listPhotosToVote = query.getPhotosToVote(user
		    .getId());
	    if (listPhotosToVote == null)
		return new StatusDTO(Status.NOT_ENOUGH_PARTICIPANTS,
			"There are not enough participants", contestStatus,
			userHasPhoto, userVoted);
	    return listPhotosToVote;

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
	    if (!query.checkUserExist(nif)
		    || !query.checkUserPassword(nif, password))
		return new StatusDTO(Status.BAD_REQUEST,
			"The nif or password you entered are not correct",
			contestStatus, false, false);
	    User user = query.getUser(nif);
	    if (!query.checkUserHasPhoto(user))
		return new StatusDTO(Status.USER_HAS_NOT_UPLOADED_PHOTO,
			"This user has not upload an image.", contestStatus,
			false, query.checkUserVoted(user));

	    return new PhotoDTO(user.getImage());

	} catch (NoSuchAlgorithmException e) {
	    System.err.println(e.getMessage());
	    return new StatusDTO(Status.INTERNAL_ERROR,
		    "Ups, something was wrong", contestStatus, false, false);
	}
    }

    @POST
    @Path("/vote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public StatusDTO voteResource(@QueryParam("nif") String nif,
	    @QueryParam("pass") String password, VoteDTO voteDTO) {
	Queries query = new Queries();
	Integer contestStatus = query.checkContestStatus();
	if (contestStatus != Status.VOTES_OPENED.intValue())
	    return new StatusDTO(Status.VOTES_CLOSED, "Votes closed!.",
		    contestStatus, false, false);
	try {
	    if (!query.checkUserExist(nif)
		    || !query.checkUserPassword(nif, password))
		return new StatusDTO(Status.BAD_REQUEST,
			"The nif or password you entered are not correct",
			contestStatus, false, false);

	    User user = query.getUser(nif);
	    Boolean userHasPhoto = query.checkUserHasPhoto(user);
	    Boolean userVoted = query.checkUserVoted(user);

	    if (!userHasPhoto)
		return new StatusDTO(Status.USER_HAS_NOT_UPLOADED_PHOTO,
			"Forbidden! This user has not uploaded an image.",
			contestStatus, userHasPhoto, userVoted);

	    if (userVoted)
		return new StatusDTO(Status.USER_HAS_ALREADY_VOTED,
			"Forbidden! This user has already voted.",
			contestStatus, userHasPhoto, userVoted);

	    if (!query.checkUserGeneratePhotoList(user.getId()))
		return new StatusDTO(Status.VOTE_WITHOUT_PHOTO_LIST,
			"Forbidden! This user want to vote illegaly.",
			contestStatus, userHasPhoto, userVoted);

	    if (!query.checkRelationBetweenUsersPhotosUploaded())
		return new StatusDTO(
			Status.PHOTOS_UPLOADED_AND_USERS_ARE_DIFFERENT,
			"Forbidden! There are a corruption case here.",
			contestStatus, userHasPhoto, userVoted);

	    Integer submittedVoteId = query.insertVote(voteDTO);
	    if (submittedVoteId == -1)
		return new StatusDTO(Status.VOTE_CANNOT_BE_SUBMITTED,
			"Vote cannot be submitted.", contestStatus,
			userHasPhoto, userVoted);

	    if (!query.setUserVoted(user)) {
		query.deleteVote(submittedVoteId);
		return new StatusDTO(Status.INTERNAL_ERROR,
			"Cannot update user status to voted", contestStatus,
			userHasPhoto, userVoted);
	    }

	    if (!query.unlinkUserPhotosToVote(user.getId())) {
		query.deleteVote(submittedVoteId);
		return new StatusDTO(
			Status.INTERNAL_ERROR,
			"Cannot unlink this user with his list of photos to vote",
			contestStatus, userHasPhoto, userVoted);
	    }

	    userVoted = true;
	    return new StatusDTO(Status.OK, "User voted successfully",
		    contestStatus, userHasPhoto, userVoted);
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

    @PUT
    @Path("/contest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public StatusDTO updateContestStatus(@QueryParam("nif") String nif,
	    @QueryParam("pass") String password, ContestDTO contestDTO) {
	Queries query = new Queries();
	Integer contestStatus = query.checkContestStatus();
	try {
	    if (!query.checkUserExist(nif)
		    || !query.checkUserPassword(nif, password))
		return new StatusDTO(Status.BAD_REQUEST,
			"The nif or password you entered are not correct",
			contestStatus, false, false);

	    User user = query.getUser(nif);
	    if (!query.checkUserIsAdmin(user))
		return new StatusDTO(Status.USER_IS_NOT_ADMIN,
			"Forbidden! User unauthorized.", contestStatus, false,
			false);

	    Integer newStatus = contestDTO.getStatus();
	    if (newStatus != Status.PRESENTATIONS_OPENED.intValue()
		    && newStatus != Status.VOTES_OPENED.intValue()
		    && newStatus != Status.CONTEST_CLOSED.intValue())
		return new StatusDTO(Status.STATUS_NOT_CORRECT,
			"Status could not be update", contestStatus, false,
			false);

	    if (!query.updateContestStatus(newStatus))
		return new StatusDTO(Status.STATUS_COULD_NOT_BE_UPDATED,
			"Status could not be updated", contestStatus, false,
			false);

	    return new StatusDTO(Status.OK, "Status updated succesfully",
		    newStatus, false, false);
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

}
