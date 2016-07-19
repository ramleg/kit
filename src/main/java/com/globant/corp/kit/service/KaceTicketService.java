package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.kace.KaceTicket;

/**
 *
 * @author ramiro.acoglanis
 */
public interface KaceTicketService {
    
    public Iterable<KaceTicket> getAll();
    public void save(KaceTicket kace);
}
