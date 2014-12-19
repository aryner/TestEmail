package com.example.aryner.testemail;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by aryner on 12/1/14.
 */
public class GMail {
    final String emailPort = "587";
    final String smtpAuth = "true";
    final String starttls = "true";
    final String emailHost = "smtp.gmail.com";

    private Context context;

    String fromEmail;
    String fromPassword;
    List<String> toEmailList;
    String emailSubject;
    String emailBody;
    String path;
    Multipart multipart;

    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;

    public GMail() {
    }

    //the path parameter would normally not be here but for the
    // sample/example case it made things easier
    public GMail(String fromEmail, String fromPassword, List toEmailList, String emailSubject, String emailBody, Context context, String path) {
        this.fromEmail = fromEmail;
        this.fromPassword = fromPassword;
        this.toEmailList = toEmailList;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
        this.context = context;
        this.path = path;

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port",emailPort);
        emailProperties.put("mail.smtp.auth",smtpAuth);
        emailProperties.put("mail.smtp.starttls.enable", starttls);
        Log.i("GMail", "Mail server properties set.");
    }

    public MimeMessage createEmailMessage() throws AddressException, MessagingException, UnsupportedEncodingException {
        multipart = new MimeMultipart();
        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));
        for(String toEmail : toEmailList) {
            Log.i("GMail", "toEmail: "+toEmail);
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        }

        emailMessage.setSubject(emailSubject);
        try {
            System.out.println("path = "+path);
            addAttachment(path);
        } catch (Exception e) {e.printStackTrace();}

        MimeBodyPart messagePart = new MimeBodyPart();
        messagePart.setText(emailBody);

        multipart.addBodyPart(messagePart);
        emailMessage.setContent(multipart); //for email with attachment
//        emailMessage.setContent(emailBody, "text/html"); // for html email
//        emailMessage.setContent(emailBody); // for a text email
        return emailMessage;
    }

    public void sendEmail() throws AddressException, MessagingException {
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(emailHost, fromEmail, fromPassword);
        Log.i("GMail", "allrecipients: "+emailMessage.getAllRecipients());
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        Log.i("GMail", "Email sent successfully.");
    }

    public void addAttachment(String fileName) throws Exception {
        BodyPart messageBodyPart = new MimeBodyPart();
        javax.activation.DataSource source = new FileDataSource(fileName);
        messageBodyPart.setDataHandler(new DataHandler(source));

        //Truncate full file path to just the filename
        Pattern pattern = Pattern.compile("[^/]*$");
        Matcher matcher = pattern.matcher(fileName);

        if(matcher.find()) {
            messageBodyPart.setFileName(matcher.group());
            fileName = matcher.group();
        }
        else {
            messageBodyPart.setFileName(fileName);
        }

        multipart.addBodyPart(messageBodyPart);
    }

    private void createFile(String fileName) {
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write("test content".getBytes());
            outputStream.close();
        } catch (Exception e) {e.printStackTrace();}

        //for testing only
        String [] files = context.fileList();
        for(int i=0; i<files.length; i++)  {
        }
    }
}
