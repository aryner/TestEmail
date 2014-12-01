package com.example.aryner.testemail;

import android.content.pm.PackageInstaller;
import android.database.DatabaseErrorHandler;
import android.location.Address;

import java.net.PasswordAuthentication;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 * Created by aryner on 12/1/14.
 */
public class MailService {
    public MailService() {
    }

    public void sendEmail() throws AddressException, MessagingException {
        String host = "smtp.gmail.com";
        String address = "vamordortest@gmail.com";

        String from = "vamordortest@gmail.com";
        String pass = "MordorTestEmail";
        String to = "vamordortest@gmail.com";

        Multipart multipart;
        String finalString = "blah";

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", address);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        System.out.println("done props");
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return  new javax.mail.PasswordAuthentication("vamordortest","MordorTestEmail");
            }
        });
        DataHandler handler = new DataHandler(new ByteArrayDataSource(finalString.getBytes(), "text/plain"));
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setDataHandler(handler);
        System.out.println("done sessions");

        multipart = new MimeMultipart();
        InternetAddress toAddress;
        toAddress = new InternetAddress(to);
        InternetAddress [] internetAddresses = {toAddress};
        message.addRecipients(Message.RecipientType.TO, internetAddresses);
        System.out.println("added recipient");
        message.setSubject("Send Auto-Mail");
        message.setContent(multipart);
        message.setText("Demo For Sending Mail in Android Automatically");

        System.out.println("transport");
        Transport transport = session.getTransport("smtp");
        System.out.println("Connecting");
        transport.connect(host, address, pass);
        System.out.println("want to send");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

        System.out.println("sent");
    }
}
