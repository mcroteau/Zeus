package xyz.ioc.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;

import org.springframework.beans.factory.annotation.Value;

public class EmailService extends Thread {

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private String port;

    @Value("${mail.smtp.username}")
    private String username;

    @Value("${mail.smtp.password}")
    private String password;

    @Value("${mail.smtp.auth}")
    private String auth;

    @Value("${mail.smtp.starttls.enabled}")
    private String starttls;


    private String protocol = "smtp";


    public boolean send(String to, String subject, String body){
        return send(to, username, subject, body);
    }

    public boolean send(String toAddress, String fromAddress, String subject, String emailBody){

        Properties props = new Properties();
        props.put("mail.smtp.auth",              auth);
        props.put("mail.smtp.starttls.enable",   starttls);
        props.put("mail.smtp.host",              host);
        props.put("mail.smtp.port",              port);
        props.put("mail.transport.protocol",     protocol);
        props.put("mail.smtp.timeout",           "10000");
        props.put("mail.smtp.connectiontimeout", "10000");


        // SSL Factory
        //props.put("mail.smtp.socketFactory.class",
        //        "javax.net.ssl.SSLSocketFactory");


        System.out.println("to : " + toAddress +
                " -> from: " + fromAddress +
                " -> username : " + username +
                " -> auth: " + auth +
                " -> starttls: " + starttls +
                " -> host: " + host +
                " -> port: " + port +
                " -> protocol: " + protocol);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        //session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddress, "Zeus"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject(subject);
            ((MimeMessage) message).setText(emailBody, "utf-8", "html");

            Transport.send(message);


        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }
}
