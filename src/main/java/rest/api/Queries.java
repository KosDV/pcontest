package rest.api;

import hibernate.manager.PictureManager;
import hibernate.manager.UrnManager;
import hibernate.manager.UserManager;
import hibernate.model.Photo;
import hibernate.model.Urn;
import hibernate.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.hibernate.HibernateException;

import rest.model.PhotoDTO;
import rest.model.RegisterDTO;
import util.Digest;
import util.PhotoUtil;

public class Queries {

    public void registerUser(RegisterDTO userBean) throws HibernateException,
    NoSuchAlgorithmException {
	Random randomGenerator = new Random();
	Integer randInt = randomGenerator.nextInt();

	String password = userBean.getPassword();
	StringBuilder passRand = new StringBuilder(password).append(randInt);
	String passwordDigested = Digest.generateSHA2(passRand.toString());
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
	String paramDigestedPassword = Digest.generateSHA2(sb.toString());
	System.out.println("SYSTEM PASS: " + userDigestedPassword);
	System.out.println("PARAM PASS: " + paramDigestedPassword);
	if (paramDigestedPassword.equals(userDigestedPassword))
	    return true;
	else
	    return false;
    }

    public User getUser(String nif) {
	UserManager usrM = new UserManager();
	return usrM.findByUserNif(nif);

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
	if (user.getImage() != null)
	    return true;
	return false;
    }

    public Boolean checkUserHasPhotoByUserNif(String nif) {
	UserManager usrM = new UserManager();
	User user = usrM.findByUserNif(nif);
	if (user.getImage() != null)
	    return true;
	return false;
    }

    public Boolean checkUserVoted(User user) {
	return user.getVoted();
    }

    public Boolean checkUserVotedByNif(String nif) {
	UserManager usrM = new UserManager();
	User user = usrM.findByUserNif(nif);
	return user.getVoted();
    }

    public Integer checkContestStatus() {
	UrnManager urnM = new UrnManager();
	Urn urn = urnM.loadUrn();
	if (urn == null || urn.getContestStatus() == null)
	    return Status.CONTEST_NOT_CREATED;
	else
	    return urn.getContestStatus();
    }

    private List<Photo> getSortedPhotoList(Integer userId) {
	PictureManager picM = new PictureManager();
	List<Photo> allPhotos = picM.loadAllPictures();

	for (int i = 0; i < allPhotos.size(); i++) {
	    if (allPhotos.get(i).getId() == userId)
		allPhotos.remove(i);
	}

	Collections.shuffle(allPhotos);

	return allPhotos;
    }

    public List<PhotoDTO> getPhotosToVote(Integer userId) {
	if (PhotoUtil.Singleton.INSTANCE.get(userId) == null){
	    List<PhotoDTO> sortedPhotoList = convertToDTO(getSortedPhotoList(userId));
	    PhotoUtil.Singleton.INSTANCE.add(userId, sortedPhotoList);
	}
	return PhotoUtil.Singleton.INSTANCE.get(userId);
    }

    private List<PhotoDTO> convertToDTO(List<Photo> photos){
	List<PhotoDTO> photosDTO = new ArrayList<PhotoDTO>();
	for(int i = 0; i< photos.size(); i++){
	    photosDTO.add(new PhotoDTO(photos.get(i)));
	}
	return photosDTO;
    }

}
