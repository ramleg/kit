package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.entity.kace.MiniTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.globant.corp.kit.service.MiniTicketService;
import com.globant.corp.kit.repo.kace.MiniTicketRepo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class MiniTicketServiceImpl implements MiniTicketService{

    @Autowired
    MiniTicketRepo repo;
    
    @Override
    public Iterable<MiniTicket> getAll() {                
        return repo.findAll();
    }

    @Override
    public void save(MiniTicket kace) {
        repo.save(kace);
    }
    
}
