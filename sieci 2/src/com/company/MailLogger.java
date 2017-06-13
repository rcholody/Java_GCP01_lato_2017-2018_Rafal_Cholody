package com.company;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailLogger implements Logger{

    private static String MAIL= "javatestmail20@gmail.com";
    private static String PASSWORD = "password123Q";
    private static String RECIPIENT = "javatestmail20@gmail.com";

    @Override
    public void log(String status, Student student) {
        String subject = "Crawler update - " + status;
        String msg = status + ":" + student.toString();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.port","465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(MAIL, PASSWORD);
                    }
                });
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(RECIPIENT));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RECIPIENT));
            message.setSubject(subject);
            message.setText(msg);

            Transport.send(message);
            }catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }
}
