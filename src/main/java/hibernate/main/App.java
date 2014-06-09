package hibernate.main;

import hibernate.manager.PictureManager;
import hibernate.manager.UrnManager;
import hibernate.manager.UserManager;
import hibernate.manager.VoteManager;
import hibernate.model.Photo;
import hibernate.model.Urn;
import hibernate.model.User;
import hibernate.model.Vote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import util.Paillier;

public class App {
	public static void main(String[] args) throws Exception {
		UserManager userManager = new UserManager();
		PictureManager pictureManager = new PictureManager();
		UrnManager urnManager = new UrnManager();
		VoteManager voteManager = new VoteManager();

		/** URN **/
		Urn urn = new Urn("Prueba");
		urnManager.saveNewUrn(urn);
		/** USERS **/
		User user1 = new User("Anna", "Andujar", "15/07/1990", "ana@kaos.com",
				"1234", "47728934R");
		user1.setVoted("YES");
		User user2 = new User("Pilar", "Macias", "15/07/1989",
				"pilar@kaos.com", "1234", "47728935R");
		User user3 = new User("Nadim", "El Taha", "15/07/1988",
				"nadim@kaos.com", "1234", "47728936R");
		User user4 = new User("kosmin", "DV", "01/01/1970", "kosmin@kaos.com",
				"1234", "48000000P");
		user4.setIsAdmin("YES");

		/** IMAGES **/
		Photo img1 = new Photo("prueba1", "Imatge de prova 1", "Img01", "");
		Photo img2 = new Photo("prueba2", "Imatge de prova 2", "Img02", "");
		Photo img3 = new Photo("prueba2", "Imatge de prova 3", "Img03", "");

		/** VOTES **/
		Paillier paillier = new Paillier();
		String m1 = "010101";
		BigInteger m1Big = new BigInteger(m1);
		BigInteger c1 = paillier.Encryption(m1Big);

		String m2 = "010001";
		BigInteger m2Big = new BigInteger(m2);
		BigInteger c2 = paillier.Encryption(m2Big);

		Vote vote1 = new Vote();
		vote1.setEncryptedVote(c1.toString());
		Vote vote2 = new Vote();
		vote2.setEncryptedVote(c2.toString());

		urn.getListVotes().add(vote1);
		urn.getListVotes().add(vote2);

		/** TRANSACTIONS **/
		userManager.saveNewUser(user1);
		userManager.saveNewUser(user2);
		userManager.saveNewUser(user3);
		userManager.saveNewUser(user4);

		pictureManager.saveNewPicture(img1, user3);
		pictureManager.saveNewPicture(img2, user2);
		pictureManager.saveNewPicture(img3, user1);

		// TODO add User_ID field to set user.voted = true
		voteManager.saveNewVote(vote1, urn);
		voteManager.saveNewVote(vote2, urn);

		List<User> users = new ArrayList<User>();
		List<Photo> images = new ArrayList<Photo>();

		users = userManager.loadAllUsers();
		images = pictureManager.loadAllPictures();

		System.out.println("======================");
		System.out.println("LIST OF USERS & IMAGES");
		System.out.println("======================");
		for (int i = 0; i < users.size(); i++) {
			User listUsers = users.get(i);
			if (listUsers.getImage() != null)
				System.out.println((i + 1) + "." + listUsers.getName() + " - "
						+ listUsers.getImage().getTitle());
		}

		System.out.println("==============");
		System.out.println("LIST OF IMAGES");
		System.out.println("==============");
		for (int i = 0; i < images.size(); i++) {
			Photo listImages = images.get(i);
			System.out.println((i + 1) + "." + listImages.getTitle() + " - "
					+ listImages.getUser().getName());
		}

		System.out.println("================");
		System.out.println("FIND USER BY NIF");
		System.out.println("================");

		System.out.println("Type your NIF");
		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			String input = bufferRead.readLine();

			User user = userManager.findByUserNif(input);
			if (user == null)
				System.err.println("There is no user with the given NIF");
			else
				System.out.println("The user with NIF:" + input + " is "
						+ user.getName() + " " + user.getSurname());

		} catch (IOException e) {
			System.err.println("There's been an error.");
			throw e;
		}

	}
}
