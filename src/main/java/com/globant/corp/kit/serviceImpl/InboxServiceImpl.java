package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.configuration.EmailConfiguration;
import com.globant.corp.kit.exception.EmailException;
import com.globant.corp.kit.entity.kit.Email;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.globant.corp.kit.service.InboxService;
import java.util.List;
/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class InboxServiceImpl implements InboxService{

    @Autowired
    EmailConfiguration config;
    
    private Folder folder;
    private UIDFolder ufolder;
    private Store store;

    @Override
    public List<Email> getAll(){
        return getEmails(1);
    }
    
    @Override
    public Email getLast(){
        try {
            this.setFolder(config.getReadFolder(), Folder.READ_ONLY);
            // Attributes & Flags for ALL messages ..
            Message msg = folder.getMessage(1);
            Email kaceEmail = getKaceEmail(msg);
            folder.close(false);
            store.close();
            return kaceEmail;
            
        } catch (MessagingException | IOException | EmailException ex) {
            Logger.getLogger(InboxServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }

    @Override
    public List<Email> getUnread(long lastRead) {
        return getEmails(lastRead);
    }

//    @Override
//    public String SendAproval(Ticket ticket) {
//        Properties props = new Properties();
//        props.put("mail.smtp.user", config.getEmailAccount());
//        props.put("mail.smtp.host", config.getSendHost());
//        props.put("mail.smtp.port", config.getSendHost());
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.socketFactory.port", config.getPort());
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.socketFactory.fallback", "false");
//        try {
//            Authenticator auth = new SMTPAuthenticator();
//            Session session = Session.getInstance(props, auth);     
//            MimeMessage msg = new MimeMessage(session);
//            msg.setText("Este es un fucking mensaje");
//            msg.setSubject("mira mira");
//            msg.setFrom(new InternetAddress(config.getEmailAccount()));
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(config.getEmailTo()));
//            Transport.send(msg);
//            return "ok";
//        } catch (Exception ex) {
//            Logger.getLogger(InboxServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//            return "something went wrong!!";
//        }
//    }
        
    private List<Email> getEmails(long getFrom){
        try {
            this.setFolder(config.getReadFolder(), Folder.READ_ONLY);
            // Attributes & Flags for ALL messages ..
            
            Message[] msgs = ufolder.getMessagesByUID(getFrom, UIDFolder.LASTUID);
            // Use a suitable FetchProfile
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.FLAGS);
            folder.fetch(msgs, fp);
            
            ArrayList<Email> emailList = new ArrayList<>();
            
            for (Message msg : msgs) {
                emailList.add(getKaceEmail(msg));
            }
            
            folder.close(false);
            store.close();
            
            return emailList;
            
        } catch (MessagingException | IOException | EmailException ex) {
            Logger.getLogger(InboxServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }
    
    private void setFolder(String inboxFolder, int folderAccessRights) throws NoSuchProviderException, MessagingException, EmailException{

        String url = null;
        // Get a Properties object
        Properties props = System.getProperties();

        // Get a Session object
        Session session = Session.getInstance(props, null);

        // Get a Store object
        store = null;
        if (url != null) {
         URLName urln = new URLName(url);
         store = session.getStore(urln);
         store.connect();
        } else {
         if (config.getProtocol() != null)
          store = session.getStore(config.getProtocol());
         else
          store = session.getStore();

         // Connect
         if (config.getSendHost() != null || config.getEmailAccount() != null || config.getEmailPasswd() != null)
          store.connect(config.getSendHost(), config.getEmailAccount(), config.getEmailPasswd());
         else
          store.connect();
        }

        // Open the Folder
        folder = store.getDefaultFolder();
        if (folder == null) {
            throw new EmailException("No default folder");
        }

        folder = folder.getFolder(inboxFolder);
        if (!folder.exists()) {
            throw new EmailException("Folder: " + inboxFolder + ",  does not exist");
        }

        if (!(folder instanceof UIDFolder)) {
            throw new EmailException("This Provider or this folder does not support UIDs");
        }

        folder.open(folderAccessRights);
        int totalMessages = folder.getMessageCount();

        if (totalMessages == 0) {
            folder.close(false);
            store.close();
            throw new EmailException("Empty folder: " + inboxFolder);
        }
        ufolder = (UIDFolder)folder;
        
    }
    
    private Email getKaceEmail(Message msg) throws MessagingException, IOException{

        Email kaceEmail = new Email();
        kaceEmail.setUid(ufolder.getUID(msg));
        Address[] from = msg.getFrom();
        String email = from == null ? null : ((InternetAddress) from[0]).getAddress();
        kaceEmail.setFrom(email);
        Address[] to = msg.getRecipients(Message.RecipientType.TO);
        kaceEmail.setTo(to[0].toString());
        kaceEmail.setSubject(msg.getSubject());
        kaceEmail.setSendDate(msg.getSentDate());
        Multipart mp = (Multipart) msg.getContent();
        BodyPart bp = mp.getBodyPart(0);
        kaceEmail.setContent((String) bp.getContent());

        return kaceEmail;
    }
        
}
