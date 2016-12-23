package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.kace.Ticket;
import com.globant.corp.kit.entity.local.ApprovalRequest;
import com.globant.corp.kit.exception.NoApprovalRequestFoudException;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */

public interface ApprovalRequestService {
    public ApprovalRequest getById(Integer id) throws NoApprovalRequestFoudException;
    public List<ApprovalRequest> getByTicketNum(Integer num);
    public ApprovalRequest getByTicketNumAndApprover(Integer num, String approver);
    public boolean exist(Integer ticketNum, String approver);
    public void save(Integer ticketNum, String approver);
    public void delete(Integer id);
}
