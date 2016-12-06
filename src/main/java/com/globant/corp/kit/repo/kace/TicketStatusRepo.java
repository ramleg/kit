package com.globant.corp.kit.repo.kace;

import com.globant.corp.kit.entity.kace.TicketStatus;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ramiro.acoglanis
 */

public interface TicketStatusRepo extends CrudRepository<TicketStatus, Integer> {
    
    public List<TicketStatus> findAllByDesc(String desc);
    
}
