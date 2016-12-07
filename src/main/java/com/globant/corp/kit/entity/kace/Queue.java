package com.globant.corp.kit.entity.kace;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ramiro.acoglanis
 */
@Entity(name="Queue")
@Table(name="HD_QUEUE")
public class Queue {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="EMAIL_USER")
    private String emailUser;
    
    @Column(name="POP_USERNAME")
    private String popUsername;
    
    @Column(name="ALT_EMAIL_ADDR")
    private String altEmailAddr;
    
    @Column(name="ALLOW_ALL_APPROVERS")
    private Integer allowed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPopUsername() {
        return popUsername;
    }

    public void setPopUsername(String popUsername) {
        this.popUsername = popUsername;
    }

    public String getAltEmailAddr() {
        return altEmailAddr;
    }

    public void setAltEmailAddr(String altEmailAddr) {
        this.altEmailAddr = altEmailAddr;
    }

    public Integer getAllowed() {
        return allowed;
    }

    public void setAllowed(Integer allowed) {
        this.allowed = allowed;
    }
    
    
    
}
