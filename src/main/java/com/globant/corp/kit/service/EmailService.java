package com.globant.corp.kit.service;

import com.globant.corp.kit.model.beans.KaceEmail;
import com.globant.corp.kit.model.beans.KaceTicket;
import java.util.ArrayList;

/**
 *
 * @author ramiro.acoglanis
 */
public interface EmailService {
    public ArrayList<KaceEmail> getAll();
    public KaceEmail getOne();
    public ArrayList<KaceEmail> getUnread(int lastRead);
    public String SendAproval(KaceTicket ticket);
    
    
}
