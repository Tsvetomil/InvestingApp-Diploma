package com.isb.utils.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailSender {

    private static final String emailSender = "noreply.investing@gmail.com";
    private static final String password = "dontreply123";


    public static void sendEmail(String emailReceiver, String subject, String text) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.debug", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailSender, password);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("noreply@investing.bg", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailReceiver));
        msg.setSubject(subject);
        msg.setContent(text, "text/html");
        msg.setSentDate(new Date());

        Transport.send(msg);
    }
}
