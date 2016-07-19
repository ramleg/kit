package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.entity.kit.Approver;
import com.globant.corp.kit.entity.kit.Comment;
import com.globant.corp.kit.entity.kit.Ticket;
import com.globant.corp.kit.repo.kit.ApproverRepository;
import com.globant.corp.kit.repo.kit.CommentRepository;
import com.globant.corp.kit.repo.kit.TicketRepository;
import com.globant.corp.kit.service.TicketService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    TicketRepository ticketRepo;
    @Autowired
    CommentRepository commentRepo;
    @Autowired
    ApproverRepository approverRepo;
    
    
    @Override
    public List<Ticket> getAll() {
        return (List<Ticket>) ticketRepo.findAll();
    }

    @Override
    public Ticket getById() {
        return ticketRepo.findByNum(123123);
    }

    @Override
    public List<Ticket> getPendingApprovals() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void saveTicket(Ticket ticket) {
        ticketRepo.save(ticket);
    }
}
