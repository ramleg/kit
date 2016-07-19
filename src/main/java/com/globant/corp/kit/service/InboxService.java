package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.kit.Email;
import com.globant.corp.kit.entity.kit.Ticket;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */
public interface InboxService {
    public List<Email> getAll();
    public Email getLast();
    public List<Email> getUnread(long lastRead);
    public String SendAproval(Ticket ticket);
    
    
}
