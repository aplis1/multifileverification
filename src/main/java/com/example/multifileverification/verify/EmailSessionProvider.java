package com.example.multifileverification.verify;

import javax.mail.Session;
import java.util.Properties;

public class EmailSessionProvider {

    public static Session getEmailSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new EmailAuthenticator("xxxx9@gmail.com", "lzzzzaassr"));
        return session;
    }
}
