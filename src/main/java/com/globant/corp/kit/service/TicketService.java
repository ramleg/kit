package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.kit.Ticket;
import java.util.List;


/**
 *
 * @author ramiro.acoglanis
 */
public interface TicketService {
    
    public List<Ticket> getAll();
    public Ticket getById();
    public List<Ticket> getPendingApprovals();
    public void saveTicket(Ticket ticket);
    
}
