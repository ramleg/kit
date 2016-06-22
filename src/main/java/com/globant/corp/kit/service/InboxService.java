package com.globant.corp.kit.service;

import com.globant.corp.kit.model.beans.Email;
import com.globant.corp.kit.model.beans.KaceTicket;
import java.util.ArrayList;

/**
 *
 * @author ramiro.acoglanis
 */
public interface InboxService {
    public ArrayList<Email> getAll();
    public Email getOne();
    public ArrayList<Email> getUnread(int lastRead);
    public String SendAproval(KaceTicket ticket);
    
    
}
