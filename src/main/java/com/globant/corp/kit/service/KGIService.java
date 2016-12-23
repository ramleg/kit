package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.kace.Ticket;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author ramiro.acoglanis
 */
public interface KGIService {
    
    public boolean update();
    public HashMap<String,String> kgiEndPoint(String isApproved, String ticketNum, String approver, String comment);
}
