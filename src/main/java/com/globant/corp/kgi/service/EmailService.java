package com.globant.corp.kgi.service;

import com.globant.corp.kgi.model.Email;
import com.globant.corp.kgi.model.mail.EmailBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class EmailService {
    
    public Email readInbox(){
        return new EmailBot().readLastMail();
    }
}
