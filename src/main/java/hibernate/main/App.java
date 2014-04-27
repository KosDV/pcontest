package hibernate.main;

import hibernate.generic.GenUserFunctions;
import hibernate.model.Image;
import hibernate.model.User;
import hibernate.specific.UserFunctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import util.HibernateUtil;

public class App {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) {

        HibernateUtil.getSession().beginTransaction();

        try {
            /** USERS **/
            User user1 = new User("Anna", "Andujar", "15/07/90",
                    "ana@kaos.com", "1234", "477", null);
            User user2 = new User("Pilar", "Macias", "15/07/89",
                    "pilar@kaos.com", "1234", "478", null);
            User user3 = new User("Nadim", "El Taha", "15/07/88",
                    "nadim@kaos.com", "1234", "479", null);

            /** IMAGES **/
            Image img1 = new Image(101, 102, "Img01", "Imatge de prova", 5,
                    user1);
            Image img2 = new Image(102, 103, "Img02", "Imatge de prova 2", 5,
                    user2);
            Image img3 = new Image(103, 104, "Img03", "Imatge de prova 3", 2,
                    user3);

            /** TRANSACTIONS **/
            HibernateUtil.getSession().save(user1);
            HibernateUtil.getSession().save(user2);
            HibernateUtil.getSession().save(user3);
            HibernateUtil.getSession().save(img1);
            HibernateUtil.getSession().save(img2);
            HibernateUtil.getSession().save(img3);
            HibernateUtil.getSession().getTransaction().commit();

        } catch (Exception e) {
            System.err.println("ERROR: There's been a problem.");
        }

        HibernateUtil.getSession().beginTransaction();

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
            System.out.println((i + 1) + "." + listUsers.getName() + " - "
                    + listUsers.getImage().getTitle());
        }

        System.out.println("==============");
        System.out.println("LIST OF IMAGES");
        System.out.println("==============");
        for (int i = 0; i < users.size(); i++) {
            Image listImages = images.get(i);
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

            UserFunctions userF2 = new UserFunctions();
            try {
                User user = userF2.findByNif(input);
                System.out.println("The user with NIF:" + input + " is "
                        + user.getName() + " " + user.getSurname());
            } catch (Exception e) {
                System.err.println("NIF error");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.err.println("There's been an error.");
        }
    }
}
