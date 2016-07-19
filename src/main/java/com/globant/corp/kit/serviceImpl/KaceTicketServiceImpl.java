package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.entity.kace.KaceTicket;
import com.globant.corp.kit.repo.kace.KaceTicketRepo;
import com.globant.corp.kit.service.KaceTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class KaceTicketServiceImpl implements KaceTicketService{

    @Autowired
    KaceTicketRepo repo;
    
    @Override
    public Iterable<KaceTicket> getAll() {
        return repo.findAll();
    }

    @Override
    public void save(KaceTicket kace) {
        repo.save(kace);
    }
    
}
