package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.kace.MiniTicket;

/**
 *
 * @author ramiro.acoglanis
 */
public interface MiniTicketService {
    
    public Iterable<MiniTicket> getAll();
    public void save(MiniTicket kace);
}
