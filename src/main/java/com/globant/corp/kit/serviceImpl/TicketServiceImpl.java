package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.configuration.AppConfig;
import com.globant.corp.kit.entity.kace.Ticket;
import com.globant.corp.kit.entity.local.ApprovalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.globant.corp.kit.repo.kace.TicketRepo;
import com.globant.corp.kit.service.QueueService;
import com.globant.corp.kit.service.TicketService;
import com.globant.corp.kit.service.TicketStatusService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    TicketRepo repo;
    
    @Autowired
    AppConfig appConfig;
    
    @Autowired
    QueueService queueService;
    
    @Autowired
    TicketStatusService tss;
    
    @Override
    public Iterable<Ticket> getAll() {
        return repo.findAll();
    }

    @Override
    public Ticket getById(Integer id) {
        return repo.findById(id);
    }
    
    @Override
    public List<Ticket> getPendingApprovalTicketsList(List<Integer> queuesIds){
        
        List<Integer> approvalStatusIds = tss.getApprovalStatusIds();
        
        List<Ticket> list =  new ArrayList<>();
        for(Ticket tkt : repo.findByStatusInAndQueueIdInAndApproversIsNot(approvalStatusIds, queuesIds, "")){
            list.add(tkt);
        }
        return list;
    }
    
    @Override
    public List<ApprovalRequest> getApprovalRequestList(List<Integer> queuesIds){
        
        List<ApprovalRequest> approvalRequestList = new ArrayList<>();
        List<Ticket> pendingApprovalTickets = getPendingApprovalTicketsList(queuesIds);
        
        for(Ticket tkt : pendingApprovalTickets){
            for(String approver : tkt.getApproversList()){
                approvalRequestList.add(new ApprovalRequest(tkt.getId(),approver));
            }
        }
        return approvalRequestList;
    }
    
}
