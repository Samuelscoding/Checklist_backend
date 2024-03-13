package com.example.api;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailController {
    
    // Konfiguration für JavaMail
    private static final Properties properties;
    static {
        properties = new Properties();
        properties.put("mail.smtp.host", "asc-mail.asc.de");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.starttls.enable", "true");
    }

    // Funktion zum Senden einer E-Mail
    public static void sendEmail(String from, String to, String subject, String body) {
        Session session = Session.getInstance(properties);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
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
