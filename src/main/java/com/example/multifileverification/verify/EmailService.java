package com.example.multifileverification.verify;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailService {
    public void sendEmail(String recipient, String subject, String content) throws MessagingException {

        // Add more properties as needed

        // Create a Session with authentication, if required

        Session emailSession = EmailSessionProvider.getEmailSession();
        try {
            Message message = new MimeMessage(emailSession);
            message.setFrom(new InternetAddress("xxx@gmail.com")); // Replace with sender email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
