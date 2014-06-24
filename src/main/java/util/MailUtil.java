package util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {

    private static String FROM_ADDRESS = "root.kdv@gmail.com";
    private static String PASSWORD = "kinakuta";
    private static String FROM_NAME = "pContest";

    public static void sendConfirmationVoteMail(String mail, String name)
	    throws UnsupportedEncodingException, MessagingException {
	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");

	Session session = Session.getDefaultInstance(props,
		new javax.mail.Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(FROM_ADDRESS,
				PASSWORD);
		    }
		});

	String msgBody = "Please wait until results come out!";

	Message msg = new MimeMessage(session);
	msg.setFrom(new InternetAddress(FROM_ADDRESS, FROM_NAME));
	msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mail,
		name));
	msg.setSubject("Your vote from pContest has been registered");
	msg.setText(msgBody);
	Transport.send(msg);
    }

    public static void sendConfirmationRegisterMail(String mail, String name)
	    throws UnsupportedEncodingException, MessagingException {
	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");

	Session session = Session.getDefaultInstance(props,
		new javax.mail.Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(FROM_ADDRESS,
				PASSWORD);
		    }
		});

	String msgBody = "The staff wish you enjoy the contest!";

	Message msg = new MimeMessage(session);
	msg.setFrom(new InternetAddress(FROM_ADDRESS, FROM_NAME));
	msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mail,
		name));
	msg.setSubject("Welcome to pContest!");
	msg.setText(msgBody);
	Transport.send(msg);
    }
}
