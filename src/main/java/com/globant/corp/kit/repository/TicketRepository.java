package com.globant.corp.kit.repository;

import com.globant.corp.kit.model.beans.Approver;
import com.globant.corp.kit.model.beans.Comment;
import com.globant.corp.kit.model.beans.Ticket;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ramiro.acoglanis
 */
public interface TicketRepository extends CrudRepository<Ticket, Integer>, TicketRepositoryCustom{
    
    public Ticket findByNum(Integer num);
}
