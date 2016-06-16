package com.globant.corp.kgi.repository;

import com.globant.corp.kgi.model.beans.KaceEmail;
import com.globant.corp.kgi.model.beans.KaceTicket;
import java.util.ArrayList;

/**
 *
 * @author ramiro.acoglanis
 */
public interface KaceEmailRepo {
    
    public ArrayList<KaceEmail> getUnread(int lastRead);
    public ArrayList<KaceEmail> getAll();
    public KaceEmail getOne();
    public String SendAproval(KaceTicket ticket);
}
