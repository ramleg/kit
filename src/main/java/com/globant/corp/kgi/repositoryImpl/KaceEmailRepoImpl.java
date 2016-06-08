package com.globant.corp.kgi.repositoryImpl;

import com.globant.corp.kgi.configuration.KaceEmailConfiguration;
import com.globant.corp.kgi.model.beans.KaceEmail;
import com.globant.corp.kgi.repository.KaceEmailRepo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
/**
 *
 * @author ramiro.acoglanis
 */
public class KaceEmailRepoImpl implements KaceEmailRepo{

    @Override
    public ArrayList<KaceEmail> getAll(String folder){
        
        Folder inbox = new KaceEmailConfiguration().getInbox(folder, Folder.READ_ONLY);
        ArrayList<KaceEmail> EmailList = new ArrayList<>();
        try {
            
            int count = inbox.getMessageCount();
            
            for (int i = 1; i == count; i++){
                
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
    
    @Override
    public KaceEmail getOne(String folder){
        return null;
    }

    @Override
    public ArrayList<KaceEmail> getFromCount(String folder, int count) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
