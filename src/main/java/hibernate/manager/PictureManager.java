package hibernate.manager;

import hibernate.model.Photo;
import hibernate.model.User;
import hibernate.specific.PictureDAO;
import hibernate.specific.UserDAO;

import java.util.List;

import org.hibernate.HibernateException;

import util.HibernateUtil;

public class PictureManager implements IPictureManager {

    private PictureDAO pictureDAO = new PictureDAO();
    private UserDAO userDAO = new UserDAO();

    public List<Photo> loadAllPictures() {
	try {
	    HibernateUtil.beginTransaction();
	    List<Photo> allPictures = pictureDAO.findAll(Photo.class);
	    HibernateUtil.commitTransaction();
	    return allPictures;
	} catch (HibernateException ex) {
	    System.err.println(ex.getMessage());
	    throw new HibernateException(ex.getCause());
	}
    }

    public void saveNewPicture(Photo pic, User user) {
	try {
	    HibernateUtil.beginTransaction();
	    user.setImage(pic);
	    pic.setUser(user);
	    userDAO.update(user);
	    HibernateUtil.commitTransaction();
	} catch (HibernateException ex) {
	    HibernateUtil.rollbackTransaction();
	    System.err.println(ex.getMessage());
	    throw new HibernateException(ex.getCause());
	}
    }

    public Photo findPictureById(Integer id) {
	try {
	    HibernateUtil.beginTransaction();
	    Photo pic = pictureDAO.findByPictureById(id);
	    HibernateUtil.commitTransaction();
	    return pic;
	} catch (HibernateException ex) {
	    System.err.println(ex.getMessage());
	    throw new HibernateException(ex.getCause());
	}

    }

}
