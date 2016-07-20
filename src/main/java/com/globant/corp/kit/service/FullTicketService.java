package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.kace.FullTicket;

/**
 *
 * @author ramiro.acoglanis
 */

public interface FullTicketService {
    public Iterable<FullTicket> getAll();
    public FullTicket getById(Integer id);
}
