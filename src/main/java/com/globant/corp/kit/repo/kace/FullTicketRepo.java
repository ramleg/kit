package com.globant.corp.kit.repo.kace;

import com.globant.corp.kit.entity.kace.FullTicket;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ramiro.acoglanis
 */
public interface FullTicketRepo extends CrudRepository<FullTicket, Integer>{
    
    public FullTicket findById(Integer id);
    
}
