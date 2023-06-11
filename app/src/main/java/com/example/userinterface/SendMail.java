package com.example.userinterface;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class SendMail {

    private static final String EMAIL = "abdihakimomar2017@gmail.com";
    private static final String PASSWORD = "qwwytdaxbrtgnojv";

    public static void send(final String recipient,  final int code,final  String Subject) throws MessagingException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");

                    Session session = Session.getInstance(props, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(EMAIL, PASSWORD);
                        }
                    });

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(EMAIL));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                    message.setSubject(Subject);
                    message.setText("Your verification code is " + code);

                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}


