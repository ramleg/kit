package com.globant.corp.kit.repo.kace;

import com.globant.corp.kit.entity.kace.Ticket;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ramiro.acoglanis
 */
public interface TicketRepo extends CrudRepository<Ticket, Integer>{
    
    public Ticket findById(Integer id);
    public List<Ticket> findByStatusInAndQueueInAndApproversIsNot(List<Integer> stausNum, List<Integer> queues, String s);
    
    
}
