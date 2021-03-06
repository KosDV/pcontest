package hibernate.main;

import hibernate.manager.ContestManager;
import hibernate.manager.UserManager;
import hibernate.model.Contest;
import hibernate.model.User;

import java.util.Random;

import util.DateUtil;
import util.DigestUtil;
import api.Status;

public class App {
    public static void main(String[] args) throws Exception {

	Random randomGenerator = new Random();
	Integer randInt = randomGenerator.nextInt();

	String password = "1234";
	StringBuilder passRand = new StringBuilder(password).append(randInt);
	String passwordDigested = DigestUtil.generateSHA2(passRand.toString());
	System.out.println("passwordDigested: " + passwordDigested);

	User user = new User("Admin", "Contest", "1970-01-01",
		"admin@kaos.com", passwordDigested, "12345678Z");
	user.setSalt(randInt);
	user.setIsAdmin(true);

	UserManager userManager = new UserManager();
	userManager.saveNewUser(user);

	Contest contest = new Contest(Status.PRESENTATIONS_OPENED,
		DateUtil.getCurrentDate());

	ContestManager contestMgr = new ContestManager();
	contestMgr.saveNewContest(contest);
    }
}
