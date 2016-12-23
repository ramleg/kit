package com.globant.corp.kit.repo.local;

import com.globant.corp.kit.entity.local.ApprovalRequest;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ramiro.acoglanis
 */

public interface ApprovalRequestRepo extends CrudRepository<ApprovalRequest, Integer>{
    public ApprovalRequest findById(Integer id);
    public List<ApprovalRequest> findByTicketNum(Integer num);
    public ApprovalRequest findByTicketNumAndApprover(Integer num, String approver);
}
