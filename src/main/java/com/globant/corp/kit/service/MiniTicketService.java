package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.kace.MiniTicket;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */
public interface MiniTicketService {
    
    public Iterable<MiniTicket> getAll();
    public HashMap<Integer, MiniTicket> getAllHashMap();
    public void save(MiniTicket kace);
}
