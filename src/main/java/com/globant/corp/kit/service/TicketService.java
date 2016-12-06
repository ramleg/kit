package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.kace.Ticket;
import com.globant.corp.kit.entity.local.ApprovalRequest;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */

public interface TicketService {
    public Iterable<Ticket> getAll();
    public Ticket getById(Integer id);
    public List<ApprovalRequest> getApprovalRequestList();
    public List<Ticket> getPendingApprovalTicketsList();
    
}
