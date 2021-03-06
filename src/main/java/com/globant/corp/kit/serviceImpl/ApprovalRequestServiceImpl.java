package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.entity.local.ApprovalRequest;
import com.globant.corp.kit.exception.NoApprovalRequestFoudException;
import com.globant.corp.kit.repo.local.ApprovalRequestRepo;
import com.globant.corp.kit.service.ApprovalRequestService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class ApprovalRequestServiceImpl implements ApprovalRequestService{

    @Autowired
    ApprovalRequestRepo repo;
    
    @Override
    public ApprovalRequest getById(Integer id) throws NoApprovalRequestFoudException{
        ApprovalRequest request = repo.findById(id);
        if (request == null){
            throw new NoApprovalRequestFoudException("Approval Request not found");
        }
        return request;
    }
    
    @Override
    public List<ApprovalRequest> getByTicketNum(Integer num) {
        return repo.findByTicketNum(num);
    }
    
    @Override
    public ApprovalRequest getByTicketNumAndApprover(Integer num, String approver) {
        return repo.findByTicketNumAndApprover(num, approver);
    }
    
    @Override
    public boolean exist(Integer ticketNum, String approver){
        ApprovalRequest apr = repo.findByTicketNumAndApprover(ticketNum, approver);
        if(apr != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void save(Integer ticketNum, String approver) {
        repo.save(new ApprovalRequest(ticketNum, approver));
    }

    @Override
    public void delete(Integer id) {
        ApprovalRequest reg = repo.findById(id);
        if(reg != null)repo.delete(reg);
    }

    

    
    
    
}
