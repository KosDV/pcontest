package api;

import hibernate.manager.PictureManager;
import hibernate.manager.UserManager;
import hibernate.model.Contest;
import hibernate.model.Photo;
import hibernate.model.Results;
import hibernate.model.User;
import hibernate.model.Vote;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.HibernateException;

import util.ContestUtil;
import util.DigestUtil;
import util.PaillierUtil;
import util.PhotoUtil;
import util.VoteUtil;
import api.model.PhotoDTO;
import api.model.RegisterDTO;
import api.model.VoteDTO;

import com.thebuzzmedia.exiftool.ExifTool;
import com.thebuzzmedia.exiftool.ExifTool.Tag;

public class Queries {

    public void registerUser(RegisterDTO userBean) throws HibernateException,
	    NoSuchAlgorithmException {
	Random randomGenerator = new Random();
	Integer randInt = randomGenerator.nextInt();

	String password = userBean.getPassword();
	StringBuilder passRand = new StringBuilder(password).append(randInt);
	String passwordDigested = DigestUtil.generateSHA2(passRand.toString());
	System.out.println("passwordDigested: " + passwordDigested);

	User user = new User(userBean.getName(), userBean.getSurname(),
		userBean.getBirth(), userBean.getEmail(), passwordDigested,
		userBean.getNif());
	user.setSalt(randInt);

	UserManager userManager = new UserManager();
	userManager.saveNewUser(user);
    }

    public Boolean checkUserPassword(String nif, String password)
	    throws NoSuchAlgorithmException {
	User user = getUser(nif);
	String userDigestedPassword = user.getPassword();
	Integer randInt = user.getSalt();
	StringBuilder sb = new StringBuilder(password).append(randInt);
	String paramDigestedPassword = DigestUtil.generateSHA2(sb.toString());
	System.out.println("SYSTEM PASS: " + userDigestedPassword);
	System.out.println("PARAM PASS: " + paramDigestedPassword);
	if (paramDigestedPassword.equals(userDigestedPassword))
	    return true;
	else
	    return false;
    }

    public User getUser(String nif) {
	try {
	    UserManager usrM = new UserManager();
	    return usrM.findByUserNif(nif);
	} catch (HibernateException ex) {
	    return null;
	}
    }

    public void insertPhoto(Photo photo, User user) throws HibernateException,
	    NoSuchAlgorithmException {

	PictureManager pictureManager = new PictureManager();
	pictureManager.saveNewPicture(photo, user);
    }

    public Boolean checkUserExist(String nif) {
	UserManager usrM = new UserManager();
	User user = usrM.findByUserNif(nif);
	if (user == null)
	    return false;
	return true;
    }

    public Boolean checkUserHasPhoto(User user) {
	if (user.getPhotos().size() != 0)
	    return true;
	return false;
    }

    public Boolean checkUserHasPhoto(String nif) {
	UserManager usrM = new UserManager();
	User user = usrM.findByUserNif(nif);
	if (user.getPhotos().size() != 0)
	    return true;
	return false;
    }

    public Boolean checkUserVoted(User user) {
	return user.getVoted();
    }

    public Boolean checkUserVoted(String nif) {
	UserManager usrM = new UserManager();
	User user = usrM.findByUserNif(nif);
	return user.getVoted();
    }

    public Boolean checkUserIsAdmin(User user) {
	if (user.getIsAdmin())
	    return true;
	return false;
    }

    public Integer checkContestStatus() {
	Contest contest = ContestUtil.Singleton.INSTANCE.get();
	if (contest == null || contest.getContestStatus() == null)
	    return Status.CONTEST_NOT_CREATED;
	else
	    return contest.getContestStatus();
    }

    public Boolean updateContestStatus(Integer newStatus) {
	try {
	    Contest contest = ContestUtil.Singleton.INSTANCE.get();
	    ContestUtil.Singleton.INSTANCE.updateContestStatus(newStatus,
		    contest);
	    return true;
	} catch (HibernateException ex) {
	    return false;
	}
    }

    private List<Photo> getShuffledPhotoList(Integer userId) {
	PictureManager picM = new PictureManager();
	List<Photo> allPhotos = picM.loadAllPictures();

	for (int i = 0; i < allPhotos.size(); i++) {
	    if (allPhotos.get(i).getUser().getId() == userId)
		allPhotos.remove(i);
	}

	Collections.shuffle(allPhotos);

	return allPhotos;
    }

    private List<Photo> getPercentagePhotoList(List<Photo> shuffledPhotoList) {
	Integer shuffledPhotoListSize = shuffledPhotoList.size();
	Float twentyFivePercent = (float) (shuffledPhotoListSize * 0.25);
	System.out.println("25%: " + twentyFivePercent);

	Integer roundedNumPhotos = Math.round(twentyFivePercent);
	System.out.println("Shuffled photo size: " + shuffledPhotoListSize);
	System.out.println("25% rounded: " + roundedNumPhotos);
	if (shuffledPhotoListSize < 5)
	    return null;
	else if (shuffledPhotoListSize >= 5 && roundedNumPhotos < 5) {
	    List<Photo> photosToVote = new ArrayList<Photo>();
	    for (int i = 0; i < 5; i++) {
		photosToVote.add(shuffledPhotoList.get(i));
	    }
	    System.out.println("returned size: " + photosToVote.size());
	    return photosToVote;
	} else {
	    List<Photo> photosToVote = new ArrayList<Photo>();
	    for (int i = 0; i < roundedNumPhotos; i++) {
		photosToVote.add(shuffledPhotoList.get(i));
	    }
	    System.out.println("returned size: " + photosToVote.size());
	    return photosToVote;
	}
    }

    public List<PhotoDTO> getPhotosToVote(Integer userId) {
	if (PhotoUtil.ListToVote.INSTANCE.get(userId) == null) {
	    List<Photo> photosToAdd = getPercentagePhotoList(getShuffledPhotoList(userId));
	    if (photosToAdd == null)
		return null;
	    List<PhotoDTO> sortedPhotoList = convertToDTO(photosToAdd);
	    PhotoUtil.ListToVote.INSTANCE.add(userId, sortedPhotoList);
	}
	return PhotoUtil.ListToVote.INSTANCE.get(userId);
    }

    public Boolean unlinkUserPhotosToVote(Integer userId) {
	if (PhotoUtil.ListToVote.INSTANCE.get(userId) != null) {
	    PhotoUtil.ListToVote.INSTANCE.remove(userId);
	    return true;
	}
	return false;
    }

    private List<PhotoDTO> convertToDTO(List<Photo> photos) {
	List<PhotoDTO> photosDTO = new ArrayList<PhotoDTO>();
	for (int i = 0; i < photos.size(); i++) {
	    photosDTO.add(new PhotoDTO(photos.get(i)));
	}
	return photosDTO;
    }

    public Integer getNumUsersUploadImage() {
	UserManager usrM = new UserManager();
	List<User> userList = usrM.loadAllUsers();
	int count = 0;
	for (int i = 0; i < userList.size(); i++) {
	    if (userList.get(i).getPhotos().size() != 0)
		count++;
	}
	return count;
    }

    public Integer getNumPhotosUploaded() {
	PictureManager picM = new PictureManager();
	List<Photo> allPhotos = picM.loadAllPictures();
	return allPhotos.size();
    }

    public Boolean checkRelationBetweenUsersPhotosUploaded() {
	if (getNumUsersUploadImage() != getNumPhotosUploaded())
	    return false;
	return true;
    }

    public Integer insertVote(VoteDTO voteDTO) {
	try {
	    Vote vote = new Vote(voteDTO.getVoteEncrypted());
	    return VoteUtil.Urn.INSTANCE.add(vote);
	} catch (HibernateException ex) {
	    return -1;
	}
    }

    public Integer insertVote(String encryptedVote) {
	try {
	    Vote vote = new Vote(encryptedVote);
	    return VoteUtil.Urn.INSTANCE.add(vote);
	} catch (HibernateException ex) {
	    return -1;
	}
    }

    public Boolean deleteVote(Integer voteId) {
	try {
	    Vote vote = VoteUtil.Urn.INSTANCE.get(voteId);
	    VoteUtil.Urn.INSTANCE.remove(vote);
	    return true;
	} catch (HibernateException ex) {
	    return false;
	}
    }

    public Boolean setUserVoted(User user) {
	try {
	    UserManager usrM = new UserManager();
	    user.setVoted(true);
	    usrM.updateUser(user);
	    return true;
	} catch (HibernateException ex) {
	    return false;
	}
    }

    public Boolean checkUserGeneratePhotoList(Integer userId) {
	if (PhotoUtil.ListToVote.INSTANCE.get(userId) == null)
	    return false;
	return true;

    }

    public Integer getIndividualVoteLength(int numPhotos) {
	if (numPhotos < 1)
	    return -1;
	return (int) (Math.log10(numPhotos) + 1);
    }

    public Integer getTotalVoteLength(int numPhotos, int individualLength) {
	Integer totalLength = numPhotos * individualLength;
	System.out.println("Num Photos: " + numPhotos + " Indidual Length: "
		+ individualLength + " Total Length: " + totalLength);
	return totalLength;
    }

    public String encryptVote(String vote) {
	PaillierUtil paillier = new PaillierUtil();
	return paillier.Encryption(new BigInteger(vote)).toString();
    }

    public List<Vote> getEncryptedVotes() {
	try {
	    return VoteUtil.Urn.INSTANCE.getAll();
	} catch (HibernateException ex) {
	    return null;
	}
    }

    public String getSumDecryptedVotes(List<Vote> encryptedVotes) {
	PaillierUtil paillier = new PaillierUtil();
	System.out.println("EncryptedVotes size: " + encryptedVotes.size());
	BigInteger product = new BigInteger(encryptedVotes.get(0)
		.getEncryptedVote());
	for (int i = 1; i < encryptedVotes.size(); i++) {
	    product = product.multiply(new BigInteger(encryptedVotes.get(i)
		    .getEncryptedVote()));
	}
	product = product.mod(paillier.nsquare);

	return paillier.Decryption(product).toString();
    }

    public List<Results> insertResults(String decryptedVotes,
	    Integer individualLength, Integer totalLength) {

	System.out.println("Decrypted votes length: " + decryptedVotes.length()
		+ " totalLength: " + totalLength);

	Contest contest = ContestUtil.Singleton.INSTANCE.get();
	List<Results> results = new ArrayList<Results>();
	Results result;
	int start = 0;
	int end = individualLength;
	int photoId = 1;
	while (true) {
	    System.out.println("start=>" + start + "; end=>" + end
		    + " photoId=>" + photoId);
	    System.out.println("photoId: " + photoId + " votes: "
		    + new Integer(decryptedVotes.substring(start, end)));
	    result = new Results(photoId, new Integer(decryptedVotes.substring(
		    start, end)), contest);
	    try {
		ContestUtil.Singleton.INSTANCE.addResult(result);
	    } catch (HibernateException ex) {
		return null;
	    }
	    results.add(result);
	    System.out.println("results size=>" + results.size());
	    start = start + individualLength;
	    end = end + individualLength;
	    photoId++;
	    if (end > totalLength)
		break;
	}
	return results;
    }

    public Boolean closeContest(Integer census, Integer numPhotos,
	    Integer numVotes, List<Results> results) {
	try {
	    Contest contest = ContestUtil.Singleton.INSTANCE.get();
	    ContestUtil.Singleton.INSTANCE.closeContest(census, numPhotos,
		    numVotes, results, contest);
	    return true;
	} catch (HibernateException ex) {
	    return false;
	}
    }

    public Contest getContest() {
	return ContestUtil.Singleton.INSTANCE.get();
    }

    public Photo getPhoto(Integer id) {
	try {
	    PictureManager picMgr = new PictureManager();
	    return picMgr.findPictureById(id);
	} catch (HibernateException ex) {
	    return null;
	}
    }

    public Map<Tag, String> getMetadata(File photo) {
	ExifTool tool = new ExifTool();
	try {
	    return tool.getImageMeta(photo, Tag.GPS_LATITUDE,
		    Tag.GPS_LONGITUDE, Tag.DATE_TIME_ORIGINAL, Tag.APERTURE,
		    Tag.EXPOSURE_TIME, Tag.ISO, Tag.IMAGE_WIDTH,
		    Tag.IMAGE_HEIGHT, Tag.MODEL, Tag.FOCAL_LENGTH, Tag.FLASH,
		    Tag.MAKE);
	} catch (IllegalArgumentException | SecurityException | IOException e) {
	    System.out.println(e.getMessage());
	    return null;
	}
    }
}
