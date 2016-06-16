package com.globant.corp.kit.model.beans;

import java.util.Date;
import javax.mail.Address;
import org.springframework.stereotype.Component;

/**
 *
 * @author ramiro.acoglanis
 */
@Component
public class KaceEmail {
    
    private long uid;
    private Address[] from;
    private Address[] to;
    private String subject;
    private String content;
    private Date sendDate;
    
    public KaceEmail() {
    }

    public Address[] getFrom() {
        return from;
    }

    public void setFrom(Address[] from) {
        this.from = from;
    }

    public Address[] getTo() {
        return to;
    }

    public void setTo(Address[] to) {
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

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
    
    
        
}
