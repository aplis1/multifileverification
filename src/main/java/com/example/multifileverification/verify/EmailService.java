package com.example.multifileverification.verify;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailService {
    public void sendEmail(String recipient, String subject, String content) throws MessagingException {
        // Setup mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        // Add more properties as needed

        // Create a Session with authentication, if required
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("asilpa09@gmail.com", "MyLilKittu@23");
            }
        });

        // Create a MimeMessage
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("asilpa09@gmail.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);
        message.setContent(content, "text/html");

        // Send the message
        Transport.send(message);
    }
}
