package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.kit.TicketReg;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */
public interface TicketRegService {
    public void save(List<TicketReg> ticket);
    public Iterable<TicketReg> getAll();
    public void clean();
}
