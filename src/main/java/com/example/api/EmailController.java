package com.example.api;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Properties;

public class EmailController {

    private static final Dotenv dotenv = Dotenv.load();
    
    // Konfiguration für JavaMail
    private static final Properties properties;
    static {
        properties = new Properties();
        properties.put("mail.smtp.host", "mail.asc.de");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.starttls.enable", "true");
    }

    // Authentifizierungsinformationen
    private static final String username = dotenv.get("MAIL_USER"); 
    private static final String password = dotenv.get("MAIL_PASSWORD");

    // Funktion zum Senden einer E-Mail
    public static void sendEmail(String to, String subject, String body) {
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("E-Mail erfolgreich gesendet an: " + to);
        } catch(MessagingException e) {
            e.printStackTrace();
            System.err.println("Fehler beim Senden der E-Mail an: " + to);
        }
    }
}
