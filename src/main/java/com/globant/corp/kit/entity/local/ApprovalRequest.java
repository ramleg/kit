package com.globant.corp.kit.entity.local;

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

@Entity(name="ApprovalRequest")
@Table(name="approval_request")
public class ApprovalRequest {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name="ticket_num")
    private Integer ticketNum;
    private String approver = "";

    public ApprovalRequest() {
    }
    
    public ApprovalRequest(Integer ticketNum, String approver) {
        this.ticketNum = ticketNum;
        this.approver = approver;
    }    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(Integer ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }
    
    
    
    
}
