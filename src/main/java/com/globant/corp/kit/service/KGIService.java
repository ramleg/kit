package com.globant.corp.kit.service;

import java.util.HashMap;


/**
 *
 * @author ramiro.acoglanis
 */
public interface KGIService {
    
    public boolean update();
    public HashMap<String,String> kgiEndPoint(String isApproved, String ticketNum, String comment, String token);
}
