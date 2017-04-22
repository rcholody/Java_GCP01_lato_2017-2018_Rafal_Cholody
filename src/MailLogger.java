

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailLogger implements Logger {
    private final String userName;
    private final String password;
    private final String recipient;

    public MailLogger(String userName, String password, String recipient) {
        this.userName = userName;
        this.password = password;
        this.recipient = recipient;
    }

    @Override
    public void log(String status, Student student) {
        Properties myProperities = new Properties();
        myProperities.put("mail.smtp.host", "poczta.agh.edu.pl");
        myProperities.put("mail.smtp.socketFactory.port", "465");
        myProperities.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        myProperities.put("mail.smtp.auth", "true");
        myProperities.put("mail.smtp.port", "465");

        Session netSession = Session.getDefaultInstance(myProperities, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        //netSession.setDebug(true); //todo delete this later

        try {
            Message message = new MimeMessage(netSession);
            message.setFrom(new InternetAddress(userName));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(status);

            message.setText("");
            if (student != null) {
                message.setText(student.toString());
            }

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("MailLogger Error");
        }
    }
}
