package com.globant.corp.kgi.repositoryImpl;

import com.globant.corp.kgi.exception.KaceEmailException;
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

    @Value("${kgi.email.settings.protocol}")
    private String protocol;
    @Value("${kgi.email.settings.read-host}")
    private String readHost;
    @Value("${kgi.email.settings.send-host}")
    private String sendHost;
    @Value("${kgi.email.settings.port}")
    private String port;
    @Value("${kgi.email.settings.emailaccount}")
    private String emailAccount;
    @Value("${kgi.email.settings.emailpasswd}")
    private String emailPasswd;
    @Value("${kgi.email.settings.emailto}")
    private String emailTo;
    @Value("${kgi.email.settings.folder}")
    private String inboxFolder;
    
    private Folder folder;
    private UIDFolder ufolder;
    private Store store;
    
    @Override
    public ArrayList<KaceEmail> getAll(){
        
        
        try {
            folder = this.getFolder(inboxFolder, Folder.READ_WRITE);
            ufolder = (UIDFolder)folder;
            
            // Attributes & Flags for ALL messages ..
            Message[] msgs = ufolder.getMessagesByUID(1, UIDFolder.LASTUID);
            // Use a suitable FetchProfile
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.FLAGS);
            folder.fetch(msgs, fp);
            
            ArrayList<KaceEmail> emailList = new ArrayList<>();
            
            for (Message msg : msgs) {
                emailList.add(getKaceEmail(msg));
            }
            
            folder.close(false);
            store.close();
            
            return emailList;
            
        } catch (MessagingException | IOException | KaceEmailException ex) {
            Logger.getLogger(KaceEmailRepoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 

    }
    
    private Folder getFolder(String inboxFolder, int folderAccessRights) throws NoSuchProviderException, MessagingException, KaceEmailException{
        
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
         if (protocol != null)
          store = session.getStore(protocol);
         else
          store = session.getStore();

         // Connect
         if (readHost != null || emailAccount != null || emailPasswd != null)
          store.connect(readHost, emailAccount, emailPasswd);
         else
          store.connect();
        }
        
        // Open the Folder
        Folder folder = store.getDefaultFolder();
        if (folder == null) {
            throw new KaceEmailException("No default folder");
        }

        folder = folder.getFolder(inboxFolder);
        if (!folder.exists()) {
            throw new KaceEmailException("Folder: " + inboxFolder + ",  does not exist");
        }

        if (!(folder instanceof UIDFolder)) {
            throw new KaceEmailException("This Provider or this folder does not support UIDs");
        }
        
        folder.open(folderAccessRights);
        int totalMessages = folder.getMessageCount();

        if (totalMessages == 0) {
            folder.close(false);
            store.close();
            throw new KaceEmailException("Empty folder: " + inboxFolder);
        }
        
        return folder;

    }
    
    private KaceEmail getKaceEmail(Message msg) throws MessagingException, IOException{
        
        KaceEmail kaceEmail = new KaceEmail();
        kaceEmail.setUid(ufolder.getUID(msg));
        kaceEmail.setFrom(msg.getFrom());
        kaceEmail.setTo(msg.getRecipients(Message.RecipientType.TO));
        kaceEmail.setSubject(msg.getSubject());
        kaceEmail.setSendDate(msg.getSentDate());
        Multipart mp = (Multipart) msg.getContent();
        BodyPart bp = mp.getBodyPart(0);
        kaceEmail.setContent((String) bp.getContent());
        
        return kaceEmail;
    }
    
    @Override
    public KaceEmail getOne(){
        
        
        try {
            UIDFolder UIDfolder = (UIDFolder) this.getFolder(inboxFolder, 1);
            UIDfolder.getMessagesByUID(2 + 1, UIDFolder.LASTUID);
        } catch (MessagingException | KaceEmailException ex) {
            Logger.getLogger(KaceEmailRepoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @Override
    public ArrayList<KaceEmail> getFromCount(int count) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String SendAproval(KaceTicket ticket) {
        Properties props = new Properties();
        props.put("mail.smtp.user", emailAccount);
        props.put("mail.smtp.host", sendHost);
        props.put("mail.smtp.port", sendHost);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        //props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", port);
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
            return "ok";
        } catch (Exception ex) {
            return ex.toString();
        }
    }
    
    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(emailAccount, emailPasswd);
        }
    }
    
    
}
