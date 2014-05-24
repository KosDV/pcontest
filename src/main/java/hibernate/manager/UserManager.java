package hibernate.manager;

import hibernate.model.User;
import hibernate.specific.UserDAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;

import util.HibernateUtil;

public class UserManager implements IUserManager {

	private UserDAO userDAO = new UserDAO();

	public User findByUserNif(String userNif) {
		User user = null;
		try {
			HibernateUtil.beginTransaction();
			user = userDAO.findByUserNif(userNif);
			HibernateUtil.commitTransaction();
		} catch (NonUniqueResultException ex) {
			System.err.println("Query returned more than one result");
			ex.printStackTrace();
			return null;
		} catch (HibernateException ex) {
			ex.printStackTrace();
			return null;
		}
		return user;
	}

	public List<User> loadAllUsers() {
		List<User> allUsers = new ArrayList<User>();

		try {
			HibernateUtil.beginTransaction();
			allUsers = userDAO.findAll(User.class);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return allUsers;
	}

	public void saveNewUser(User user) {
		try {
			HibernateUtil.beginTransaction();
			userDAO.save(user);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.err.println("ERROR: Saving User: " + user.getNif());
			HibernateUtil.rollbackTransaction();
		}
	}

}
