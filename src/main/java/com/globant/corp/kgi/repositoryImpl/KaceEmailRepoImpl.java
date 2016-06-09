package com.globant.corp.kgi.repositoryImpl;

import com.globant.corp.kgi.model.beans.KaceEmail;
import com.globant.corp.kgi.model.beans.KaceTicket;
import com.globant.corp.kgi.repository.KaceEmailRepo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
/**
 *
 * @author ramiro.acoglanis
 */
@Repository
public class KaceEmailRepoImpl implements KaceEmailRepo{

    @Value("${kgi.email.settings.imap-host}")
    private String imapHost;
    @Value("${kgi.email.settings.smtp-host}")
    private String smtpHost;
    @Value("${kgi.email.settings.smtp-port}")
    private String smtpPort;
    @Value("${kgi.email.settings.emailaccount}")
    private String emailAccount;
    @Value("${kgi.email.settings.emailpasswd}")
    private String emailPasswd;
    @Value("${kgi.email.settings.emailto}")
    private String emailTo;
    
    @Override
    public ArrayList<KaceEmail> getAll(String folder){
        
        Folder inbox = this.getInbox(folder, Folder.READ_ONLY);
        ArrayList<KaceEmail> EmailList = new ArrayList<>();
        try {
            int count = inbox.getMessageCount();
            
            for (int i = 1; i <= count; i++){
                
                KaceEmail kaceEmail = new KaceEmail();
                Message msg = inbox.getMessage(i);
                Address[] fromArray = msg.getFrom();
                ArrayList<String> fromList = new ArrayList<>();
                for (Address address : fromArray) {
                    fromList.add(address.toString());
                }
                kaceEmail.setFrom(fromList);
                Multipart mp = (Multipart) msg.getContent();
                BodyPart bp = mp.getBodyPart(0);
                kaceEmail.setSubject(msg.getSubject());
                kaceEmail.setSendDate(msg.getSentDate().toString());
                kaceEmail.setContent(bp.getContent().toString());
                EmailList.add(kaceEmail);
            }
            return EmailList;
        } catch (MessagingException | IOException ex) {
            return null;
        }
    }
    
    private Folder getInbox(String folder, int access){
        try {
            Folder inbox;
            Store store;
            Properties prop = new Properties();
            prop.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getInstance(prop, null);
            store = session.getStore();
            store.connect(imapHost, emailAccount, emailPasswd);
            inbox = store.getFolder(folder);
            int count = inbox.getMessageCount();
            inbox.open(access);//Folder.READ_ONLY = 1 // Folder.READ_WRITE = 2
            return inbox;
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(KaceEmailRepoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (MessagingException ex) {
            Logger.getLogger(KaceEmailRepoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public KaceEmail getOne(String folder){
        return null;
    }

    @Override
    public ArrayList<KaceEmail> getFromCount(String folder, int count) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SendAproval(KaceTicket ticket) {
        Properties props = new Properties();
        props.put("mail.smtp.user", emailAccount);
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        //props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", smtpPort);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        try {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);     
            MimeMessage msg = new MimeMessage(session);
            msg.setText("Este es un fucking mensaje");
            msg.setSubject("mira mira");
            msg.setFrom(new InternetAddress(emailAccount));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            Transport.send(msg);
        } catch (Exception mex) {
            mex.printStackTrace();
        }
    }
    
    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(emailAccount, emailPasswd);
        }
    }
    
    
}
