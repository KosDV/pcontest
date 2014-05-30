package hibernate.manager;

import hibernate.model.Photo;
import hibernate.model.User;
import hibernate.specific.PictureDAO;
import hibernate.specific.UserDAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import util.HibernateUtil;

public class PictureManager implements IPictureManager {

	private PictureDAO pictureDAO = new PictureDAO();
	private UserDAO userDAO = new UserDAO();

	@SuppressWarnings("unchecked")
	public List<Photo> loadAllPictures() {
		List<Photo> allPictures = new ArrayList<Photo>();
		try {
			HibernateUtil.beginTransaction();
			allPictures = pictureDAO.findAll(Photo.class);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.err.println("Error while loading all pictures.");
		}
		return allPictures;
	}

	public Boolean saveNewPicture(Photo pic, User user) {
		try {
			HibernateUtil.beginTransaction();
			user.setImage(pic);
			pic.setUser(user);
			userDAO.update(user);
			HibernateUtil.commitTransaction();
			return true;

		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			System.err.println("Error while saving picture: " + pic.getTitle());
			return false;
		}
	}

	public Photo findPictureById(Integer id) {
		Photo pic = null;
		try {
			HibernateUtil.beginTransaction();
			pic = pictureDAO.findByPictureById(id);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return pic;
	}

}
