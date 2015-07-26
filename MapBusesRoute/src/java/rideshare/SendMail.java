/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rideshare;

/**
 *
 * @author Server
 */
    import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
public class SendMail {




    public static void main(String[] args) {

        String host = "smtp.gmail.com";
        final String user = "togetherrides@gmail.com";//change accordingly  
        final String password = "!@#!@#!@#";//change accordingly  

        String to = "mimansa.jaiswal@gmail.com";//change accordingly  

         
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.port", "587");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session l_session = Session.getInstance(props,
                new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        l_session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(l_session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("hiii");
            message.setText("test mail");

             
            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

