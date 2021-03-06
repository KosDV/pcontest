package api;

import hibernate.manager.UserManager;
import hibernate.model.Contest;
import hibernate.model.Photo;
import hibernate.model.Results;
import hibernate.model.User;
import hibernate.model.Vote;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

import servers.conf.ServerConfigurator;
import util.DigestUtil;
import util.PhotoUtil;
import api.model.ClosureDTO;
import api.model.ContestDTO;
import api.model.PhotoDTO;
import api.model.PhotosDTO;
import api.model.RegisterDTO;
import api.model.ResultsDTO;
import api.model.StatusDTO;
import api.model.UserDTO;
import api.model.VoteDTO;
import api.model.VotedPhotosDTO;

import com.thebuzzmedia.exiftool.ExifTool.Tag;

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
	    return new StatusDTO(Status.PRESENTATIONS_CLOSED,
		    "Registration is closed.", contestStatus, false, false);

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

		query.sendConfirmationRegisterMail(user.getEmail(),
			user.getName());
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
		return new StatusDTO(Status.UNAUTHORIZED,
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
	String filename = "photo.jpg";
	Queries query = new Queries();
	Integer contestStatus = query.checkContestStatus();
	if (contestStatus != Status.PRESENTATIONS_OPENED.intValue())
	    return new StatusDTO(Status.PRESENTATIONS_CLOSED,
		    "Presentations closed!.", contestStatus, false, false);

	try {
	    if (!query.checkUserExist(nif)
		    || !query.checkUserPassword(nif, password))
		return new StatusDTO(Status.UNAUTHORIZED,
			"The nif or password you entered are not correct",
			contestStatus, false, false);

	    User user = query.getUser(nif);
	    Boolean userHasPhoto = query.checkUserHasPhoto(user);
	    Boolean userVoted = query.checkUserVoted(user);

	    if (userHasPhoto)
		return new StatusDTO(Status.USER_HAS_ALLREADY_UPLOADED_PHOTO,
			"Forbidden! This user allready has an image.",
			contestStatus, userHasPhoto, userVoted);

	    if (photoS == null)
		return new StatusDTO(Status.BAD_REQUEST,
			"Please, upload a photo", contestStatus, userHasPhoto,
			userVoted);

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
	    StringBuilder pathRand = new StringBuilder(nif).append(salt);
	    String pathDigested = DigestUtil.generateSHA2(pathRand.toString());

	    StringBuilder pathLocationBuilder = new StringBuilder(
		    ServerConfigurator.getPhotopath());
	    pathLocationBuilder.append(pathDigested).append(File.separator);
	    String pathLocation = pathLocationBuilder.toString();
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

	    Map<Tag, String> metadataMap = query.getMetadata(newFile);
	    if (metadataMap == null)
		return new StatusDTO(Status.WRONG_METADATA,
			"This file cannot be uploaded", contestStatus,
			userHasPhoto, userVoted);

	    Photo photo = new Photo();
	    photo.setTitle(title);
	    photo.setDescription(description);
	    photo.setFilename(filename);
	    photo.setSalt(salt);
	    photo.setUrl(imageURL);
	    photo.setGpsLatitude(metadataMap.get(Tag.GPS_LATITUDE));
	    photo.setGpsLongitude(metadataMap.get(Tag.GPS_LONGITUDE));
	    photo.setDate(metadataMap.get(Tag.DATE_TIME_ORIGINAL));
	    photo.setAperture(metadataMap.get(Tag.APERTURE));
	    photo.setExposureTime(metadataMap.get(Tag.EXPOSURE_TIME));
	    photo.setISO(metadataMap.get(Tag.ISO));
	    photo.setWidth(metadataMap.get(Tag.IMAGE_WIDTH));
	    photo.setHeight(metadataMap.get(Tag.IMAGE_HEIGHT));
	    photo.setModel(metadataMap.get(Tag.MODEL));
	    photo.setFocalLength(metadataMap.get(Tag.FOCAL_LENGTH));
	    photo.setAperture(metadataMap.get(Tag.FLASH));
	    photo.setMake(metadataMap.get(Tag.MAKE));

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
		return new StatusDTO(Status.UNAUTHORIZED,
			"The nif or password you entered are not correct",
			contestStatus, false, false);

	    User user = query.getUser(nif);
	    Boolean userHasPhoto = query.checkUserHasPhoto(user);
	    Boolean userVoted = query.checkUserVoted(user);

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
	    int numParticipants = query.getNumUsersUploadImage();
	    if (listPhotosToVote == null || numParticipants < 15)
		return new StatusDTO(Status.NOT_ENOUGH_PARTICIPANTS,
			"There are not enough participants", contestStatus,
			userHasPhoto, userVoted);

	    Integer numPhotosTotal = query.getNumPhotosUploaded();

	    Integer individualVoteLength = query
		    .getIndividualVoteLength(numPhotosTotal);
	    Integer totalVoteLength = query.getTotalVoteLength(numPhotosTotal,
		    individualVoteLength);
	    BigInteger n = new BigInteger(ServerConfigurator.getN());
	    BigInteger g = new BigInteger(ServerConfigurator.getG());

	    Integer votes;
	    if (numPhotosTotal >= 15 && numPhotosTotal < 39)
		votes = 3;
	    else if (numPhotosTotal >= 39)
		votes = 5;
	    else
		votes = 0;

	    PhotosDTO photos = new PhotosDTO(listPhotosToVote,
		    individualVoteLength, totalVoteLength, n, g, votes);

	    return photos;
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
		return new StatusDTO(Status.UNAUTHORIZED,
			"The nif or password you entered are not correct",
			contestStatus, false, false);
	    User user = query.getUser(nif);
	    if (!query.checkUserHasPhoto(user))
		return new StatusDTO(Status.USER_HAS_NOT_UPLOADED_PHOTO,
			"This user has not upload an image.", contestStatus,
			false, query.checkUserVoted(user));

	    return new PhotoDTO(user.getPhotos().get(0));

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
		return new StatusDTO(Status.UNAUTHORIZED,
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
		return new StatusDTO(Status.USER_TRY_VOTE_PHOTO_NOT_LISTED,
			"Forbidden! This user want to vote illegaly.",
			contestStatus, userHasPhoto, userVoted);

	    if (!query.checkRelationBetweenUsersPhotosUploaded())
		return new StatusDTO(
			Status.PHOTOS_UPLOADED_AND_USERS_ARE_DIFFERENT,
			"Forbidden! There are a corruption case here.",
			contestStatus, userHasPhoto, userVoted);
	    // only for testing, in production votes should be encrypted by the
	    // client
	    String encryptedVote = query
		    .encryptVote(voteDTO.getVoteEncrypted());
	    // in production use insertVote(VoteDTO voteDTO) instead
	    // insertVote(String vote)
	    Integer submittedVoteId = query.insertVote(encryptedVote);
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
	    query.sendConfirmationVoteMail(user.getEmail(), user.getName());
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
		return new StatusDTO(Status.UNAUTHORIZED,
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

	    List<PhotoDTO> listPhotosToVote = query.getPhotosToVote(user
		    .getId());

	    Integer numParticipants = query.getNumUsersUploadImage();
	    if (newStatus == Status.VOTES_OPENED.intValue()
		    && (listPhotosToVote == null || numParticipants < 15))
		return new StatusDTO(Status.NOT_ENOUGH_PARTICIPANTS,
			"There are not enough participants", contestStatus,
			false, false);

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

    @POST
    @Path("/contest")
    @Produces(MediaType.APPLICATION_JSON)
    public StatusDTO closeContest(@QueryParam("nif") String nif,
	    @QueryParam("pass") String password) {
	Queries query = new Queries();
	Integer contestStatus = query.checkContestStatus();
	if (contestStatus != Status.VOTES_OPENED.intValue())
	    return new StatusDTO(Status.VOTES_CLOSED, "Check contest state.",
		    contestStatus, false, false);
	try {
	    if (!query.checkUserExist(nif)
		    || !query.checkUserPassword(nif, password))
		return new StatusDTO(Status.UNAUTHORIZED,
			"The nif or password you entered are not correct",
			contestStatus, false, false);

	    User user = query.getUser(nif);
	    if (!query.checkUserIsAdmin(user))
		return new StatusDTO(Status.USER_IS_NOT_ADMIN,
			"Forbidden! User unauthorized.", contestStatus, false,
			false);

	    List<Vote> encryptedVotes = query.getEncryptedVotes();
	    if (encryptedVotes.isEmpty())
		return new StatusDTO(Status.BAD_REQUEST, "There are no votes.",
			contestStatus, false, false);

	    Integer census = query.getNumUsersUploadImage();

	    Integer numVotes = encryptedVotes.size();

	    if (numVotes > census)
		return new StatusDTO(Status.MORE_VOTES_THAN_CENSUS,
			"There are more votes than participants!.",
			contestStatus, false, false);

	    String votesDecrypted = query.getSumDecryptedVotes(encryptedVotes);
	    System.out.println("Sum of decrypted votes: " + votesDecrypted);

	    Integer numPhotos = query.getNumPhotosUploaded();

	    Integer individualLength = query.getIndividualVoteLength(numPhotos);

	    Integer totalLength = query.getTotalVoteLength(numPhotos,
		    individualLength);

	    Integer voteLength = votesDecrypted.length();

	    Integer difference = 0;
	    // Integer.parseInt(Integer.toString(9) + Integer.toString(10));
	    if (voteLength > totalLength)
		return new StatusDTO(Status.VOTES_DECRYPTED_WRONG_SIZE,
			"There are a problem with submitted votes",
			contestStatus, false, false);
	    else if (voteLength < totalLength)
		difference = (totalLength - voteLength);

	    List<Results> results = query.insertResults(votesDecrypted,
		    individualLength, totalLength, difference);
	    if (results.isEmpty())
		return new StatusDTO(Status.CANNOT_INSERT_RESULTS,
			"Results cannot be inserted successfully",
			contestStatus, false, false);

	    if (!query.closeContest(census, numPhotos, numVotes, results))
		return new StatusDTO(Status.CANNOT_CLOSE_CONTEST,
			"Contest cannot be closed", contestStatus, false, false);

	    contestStatus = query.checkContestStatus();
	    return new StatusDTO(Status.OK, "Contest closed succesfully",
		    contestStatus, false, false);
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
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/contest")
    public Object getResults(@QueryParam("nif") String nif,
	    @QueryParam("pass") String password) {
	Queries query = new Queries();
	Integer contestStatus = query.checkContestStatus();
	if (contestStatus != Status.CONTEST_CLOSED.intValue())
	    return new StatusDTO(Status.CONTEST_OPENED, "Check contest state.",
		    contestStatus, false, false);
	try {
	    if (!query.checkUserExist(nif)
		    || !query.checkUserPassword(nif, password))
		return new StatusDTO(Status.UNAUTHORIZED,
			"The nif or password you entered are not correct",
			contestStatus, false, false);
	    Contest contest = query.getContest();

	    List<ResultsDTO> resultsDTO = new ArrayList<>();

	    List<Results> results = query.getContest().getResults();
	    ResultsDTO resultDTO;
	    Integer votes;
	    Photo photo;

	    for (int i = 0; i < results.size(); i++) {
		photo = query.getPhoto(results.get(i).getPhotoId());
		votes = results.get(i).getVotes();
		resultDTO = new ResultsDTO(photo.getUser().getNif(), votes,
			new VotedPhotosDTO(photo));
		resultsDTO.add(resultDTO);
	    }

	    if (resultsDTO.isEmpty())
		return new StatusDTO(Status.CANNOT_SHOW_RESULTS,
			"Results cannot be recovered from system",
			contestStatus, query.checkUserHasPhoto(nif),
			query.checkUserVoted(nif));

	    Integer census = contest.getNumCensus();
	    Integer numPhotos = contest.getNumPhotos();
	    Integer numVotes = contest.getNumVotes();

	    return new ClosureDTO(census, numPhotos, numVotes, resultsDTO);
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
