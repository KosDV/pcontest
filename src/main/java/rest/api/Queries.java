package rest.api;

import hibernate.manager.UserManager;
import hibernate.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.HibernateException;

import rest.model.RegisterDTO;
import util.PasswordDigest;

public class Queries {

	public void registerUser(RegisterDTO userBean)
			throws HibernateException, NoSuchAlgorithmException {
		Random randomGenerator = new Random();
		Integer randInt = randomGenerator.nextInt();

		String password = userBean.getPassword();
		StringBuilder passRand = new StringBuilder(password).append(randInt);
		String passwordDigested = PasswordDigest.generateSHA2(passRand.toString());
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
		UserManager usrM = new UserManager();
		User user = usrM.findByUserNif(nif);
		String userDigestedPassword = user.getPassword();
		Integer randInt = user.getSalt();
		StringBuilder sb = new StringBuilder(password).append(randInt);
		String paramDigestedPassword = PasswordDigest.generateSHA2(sb.toString());
		System.out.println("SYSTEM PASS: " + userDigestedPassword);
		System.out.println("PARAM PASS: " + paramDigestedPassword);
		if (paramDigestedPassword.equals(userDigestedPassword))
			return true;
		else
			return false;
	}

	public Boolean checkUserExist(String nif) {
		UserManager usrM = new UserManager();
		User user = usrM.findByUserNif(nif);
		if (user == null)
			return false;
		return true;
	}

}
