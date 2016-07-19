package com.globant.corp.kit.repo.kit;

import com.globant.corp.kit.entity.kit.Ticket;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ramiro.acoglanis
 */
public interface TicketRepository extends CrudRepository<Ticket, Integer>{
    
    public Ticket findByNum(Integer num);
}
