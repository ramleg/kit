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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ramiro.acoglanis
 */
@Configuration
@EnableAutoConfiguration
public class KaceEmailConfiguration {
    
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
    
    public Folder getInbox(String folder, int access) {
        
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
}
