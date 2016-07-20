package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.entity.kace.FullTicket;
import com.globant.corp.kit.repo.kace.FullTicketRepo;
import com.globant.corp.kit.service.FullTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class FullTicketServiceImpl implements FullTicketService{

    @Autowired
    FullTicketRepo repo;
    
    @Override
    public Iterable<FullTicket> getAll() {
        return repo.findAll();
    }

    @Override
    public FullTicket getById(Integer id) {
        return repo.findById(id);
    }
    
}
