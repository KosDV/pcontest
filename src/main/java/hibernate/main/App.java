package hibernate.main;

import hibernate.generic.GenUserFunctions;
import hibernate.model.Image;
import hibernate.model.Urn;
import hibernate.model.User;
import hibernate.model.Vote;
import hibernate.specific.UserFunctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import util.HibernateUtil;
import util.Paillier;

public class App {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {

		HibernateUtil.getSession().beginTransaction();
		try {
			/** URN **/
			Urn urn = new Urn("Prueba");
			HibernateUtil.getSession().save(urn);

			/** USERS **/
			User user1 = new User("Anna", "Andujar", "15/07/1990",
					"ana@kaos.com", "1234", "477");
			user1.setVoted("YES");
			User user2 = new User("Pilar", "Macias", "15/07/1989",
					"pilar@kaos.com", "1234", "478");
			User user3 = new User("Nadim", "El Taha", "15/07/1988",
					"nadim@kaos.com", "1234", "479");
			User user4 = new User("kosmin", "DV", "01/01/1970",
					"kosmin@kaos.com", "1234", "480");
			user4.setIsAdmin("YES");

			/** IMAGES **/
			Image img1 = new Image(101, 102, "Img01", "Imatge de prova", user1);
			Image img2 = new Image(102, 103, "Img02", "Imatge de prova 2",
					user2);
			Image img3 = new Image(103, 104, "Img03", "Imatge de prova 3",
					user3);

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
			vote1.setUrn(urn);
			Vote vote2 = new Vote();
			vote2.setEncryptedVote(c2.toString());
			vote2.setUrn(urn);

			urn.getListVotes().add(vote1);
			urn.getListVotes().add(vote2);

			/** TRANSACTIONS **/
			HibernateUtil.getSession().save(user1);
			HibernateUtil.getSession().save(user2);
			HibernateUtil.getSession().save(user3);
			HibernateUtil.getSession().save(user4);

			HibernateUtil.getSession().save(img1);
			HibernateUtil.getSession().save(img2);
			HibernateUtil.getSession().save(img3);

			HibernateUtil.getSession().save(vote1);
			HibernateUtil.getSession().save(vote2);

			HibernateUtil.getSession().getTransaction().commit();

		} catch (Exception e) {
			System.err.println("ERROR: There's been a problem.");
			HibernateUtil.getSession().getTransaction().rollback();
			throw e;
		}

		HibernateUtil.getSession().beginTransaction();
		try {
			GenUserFunctions userF = new GenUserFunctions();
			List<User> users = new ArrayList<User>();
			List<Image> images = new ArrayList<Image>();

			users = userF.findAll(User.class);
			images = userF.findAll(Image.class);

			HibernateUtil.getSession().getTransaction().commit();
			System.out.println("======================");
			System.out.println("LIST OF USERS & IMAGES");
			System.out.println("======================");
			for (int i = 0; i < users.size(); i++) {
				User listUsers = users.get(i);
				if (listUsers.getImage() != null)
					System.out.println((i + 1) + "." + listUsers.getName()
							+ " - " + listUsers.getImage().getTitle());
			}

			System.out.println("==============");
			System.out.println("LIST OF IMAGES");
			System.out.println("==============");
			for (int i = 0; i < images.size(); i++) {
				Image listImages = images.get(i);
				System.out.println((i + 1) + "." + listImages.getTitle()
						+ " - " + listImages.getUser().getName());
			}

			System.out.println("================");
			System.out.println("FIND USER BY NIF");
			System.out.println("================");

			System.out.println("Type your NIF");
			try {
				BufferedReader bufferRead = new BufferedReader(
						new InputStreamReader(System.in));
				String input = bufferRead.readLine();

				UserFunctions userF2 = new UserFunctions();

				User user = userF2.findByNif(input);
				if (user == null)
					System.err.println("There is no user with the given NIF");
				else
					System.out.println("The user with NIF:" + input + " is "
							+ user.getName() + " " + user.getSurname());

			} catch (IOException e) {
				System.err.println("There's been an error.");
				throw e;
			}
		} catch (Exception e) {
			System.err.println("ERROR: There's been a problem.");
			HibernateUtil.getSession().getTransaction().rollback();
			throw e;
		}

	}
}
