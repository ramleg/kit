package com.globant.corp.kgi.model.mail;

import com.globant.corp.kgi.model.Email;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
/**
 *
 * @author ramiro.acoglanis
 */
public class EmailBot {
    
    private Properties props = new Properties();
    private Email email = new Email();
    
    public Email readLastMail(){
        
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", "kaceintegration@gmail.com", "Globant123");
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message msg = inbox.getMessage(inbox.getMessageCount());
            
            Address[] fromArray = msg.getFrom();
            ArrayList<String> fromList = new ArrayList<>();
            for (Address address : fromArray) {
                fromList.add(address.toString());
            }
            email.setFrom(fromList);
            
            Multipart mp = (Multipart) msg.getContent();
            BodyPart bp = mp.getBodyPart(0);
            email.setSubject(msg.getSubject());
            email.setSendDate(msg.getSentDate().toString());
            email.setContent(bp.getContent().toString());
        } catch (MessagingException | IOException mex) {
            return null;
        }
        
        return email;
    }
    
}
