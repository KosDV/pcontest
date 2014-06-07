package rest.api;

import hibernate.manager.UserManager;
import hibernate.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.hibernate.HibernateException;

import rest.model.UserBean;

public class Queries {
	Integer OK = 0;
	Integer KO = -1;

	public void registerUser(UserBean userBean) throws HibernateException,
			NoSuchAlgorithmException {
		Random randomGenerator = new Random();
		Integer randInt = randomGenerator.nextInt();
		// user.setRandInt(randInt);

		String password = userBean.getPassword();
		StringBuilder passRand = new StringBuilder(password).append(randInt);
		String passwordDigested = generateSHA2(passRand.toString());
		System.out.println("passwordDigested: " + passwordDigested);
		// user.setPassword(passwordDigested);

		User user = new User(userBean.getName(), userBean.getSurname(),
				userBean.getBirth(), userBean.getEmail(), passwordDigested,
				userBean.getNif());
		user.setSalt(randInt);

		UserManager userManager = new UserManager();
		userManager.saveNewUser(user);
	}

	public Integer checkUserPassword(String nif, String password)
			throws NoSuchAlgorithmException {
		UserManager usrM = new UserManager();
		User user = usrM.findByUserNif(nif);
		String userDigestedPassword = user.getPassword();
		Integer randInt = user.getSalt();
		StringBuilder sb = new StringBuilder(password).append(randInt);
		String paramDigestedPassword = generateSHA2(sb.toString());
		System.out.println("SYSTEM PASS: " + userDigestedPassword);
		System.out.println("PARAM PASS: " + paramDigestedPassword);
		if (paramDigestedPassword.equals(userDigestedPassword))
			return OK;
		else
			return KO;
	}

	public Boolean checkUserExist(String nif) {
		UserManager usrM = new UserManager();
		User user = usrM.findByUserNif(nif);
		if (user == null)
			return false;
		return true;
	}

	public String generateSHA2(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(password.getBytes());
		byte byteData[] = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}
}
