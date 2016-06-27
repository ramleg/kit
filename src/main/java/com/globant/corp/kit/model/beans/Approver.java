package com.globant.corp.kit.model.beans;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author ramiro.acoglanis
 */
@Entity(name = "Approver")
@Table(name = "ticket_approvals")
public class Approver {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id = 0;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="fk_ticket")
    private Ticket ticket;
    private String user;
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    
    
}
