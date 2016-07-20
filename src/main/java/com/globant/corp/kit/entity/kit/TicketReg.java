package com.globant.corp.kit.entity.kit;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ramiro.acoglanis
 */
@Entity(name="TicketReg")
@Table(name="ticket_reg")
public class TicketReg {
    
    @Id
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    private String alert;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }
    
    
}
