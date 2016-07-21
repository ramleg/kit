
package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.entity.local.TicketReg;
import com.globant.corp.kit.repo.local.TicketRegRepo;
import com.globant.corp.kit.service.TicketRegService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class TicketRegServiceImpl implements TicketRegService{

    @Autowired
    TicketRegRepo repo;
    
    @Override
    public void save(List<TicketReg> ticket) {
        repo.save(ticket);
    }

    @Override
    public Iterable<TicketReg> getAll() {
        return repo.findAll();
    }

    @Override
    public void clean() {
        repo.deleteAll();
    }
    
}
