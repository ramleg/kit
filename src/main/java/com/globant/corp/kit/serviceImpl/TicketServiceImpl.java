package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.model.beans.Approver;
import com.globant.corp.kit.model.beans.Comment;
import com.globant.corp.kit.model.beans.Ticket;
import com.globant.corp.kit.repository.ApproverRepository;
import com.globant.corp.kit.repository.CommentRepository;
import com.globant.corp.kit.repository.TicketRepository;
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
    TicketRepository TicketRepo;
    @Autowired
    CommentRepository CommentRepo;
    @Autowired
    ApproverRepository approverRepo;
    
    
    @Override
    public List<Ticket> getAll() {
        return (List<Ticket>) TicketRepo.findAll();
    }

    @Override
    public Ticket getById() {
        return TicketRepo.findByNum(123123);
    }

    @Override
    public List<Ticket> getPendingApprovals() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void saveTicket(Ticket ticket) {
        Iterable<Comment> coments = ticket.getComments();
        Iterable<Approver> approvers = ticket.getApprovers();
        
        ticket.setComments(null);
        ticket.setApprovers(null);
        
        TicketRepo.save(ticket);
        CommentRepo.save(coments);
        approverRepo.save(approvers);
    }
}
