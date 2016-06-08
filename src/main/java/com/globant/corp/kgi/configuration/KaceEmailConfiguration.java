package com.globant.corp.kgi.configuration;

import com.globant.corp.kgi.repositoryImpl.KaceEmailRepoImpl;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author ramiro.acoglanis
 */
public class KaceEmailConfiguration {
    public Folder getInbox(String folder, int access) {
        
        try {
            Folder inbox;
            Store store;
            Properties prop = new Properties();
            prop.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getInstance(prop, null);
            store = session.getStore();
            store.connect("imap.gmail.com", "kaceintegration@gmail.com", "Globant123");
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
}
