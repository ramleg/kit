package com.globant.corp.kgi.model;

import java.util.ArrayList;
import javax.mail.Address;
import org.springframework.stereotype.Component;

/**
 *
 * @author ramiro.acoglanis
 */
@Component
public class Email {
    
    private ArrayList<String> from;
    private String to;
    private String subject;
    private String content;
    private String sendDate;
    
    public Email() {
    }

    public ArrayList<String> getFrom() {
        return from;
    }

    public void setFrom(ArrayList<String> from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
    
    
        
}
