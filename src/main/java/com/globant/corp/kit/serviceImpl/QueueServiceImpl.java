package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.entity.kace.Queue;
import com.globant.corp.kit.entity.kace.Ticket;
import com.globant.corp.kit.repo.kace.QueueRepo;
import com.globant.corp.kit.service.QueueService;
import com.globant.corp.kit.service.TicketService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class QueueServiceImpl implements QueueService{
    
    @Autowired
    QueueRepo repo;
    
    @Autowired
    TicketService ticketService;
    
    @Override
    public List<Queue> getAllwedQueues() {
        return repo.findByAllowed(1);
    }
    
    @Override
    public List<Integer> getQueuesIds(List<Queue> queueList) {
        List<Integer> returnList = new ArrayList<>();
        for(Queue queue : queueList){
            returnList.add(queue.getId());
        }
        return  returnList;
    }
    
    @Override
    public String getEmail(int ticketId) {
        Ticket ticket = ticketService.getById(ticketId);
        Queue queue = repo.findById(ticket.getQueueId());
        return queue.getPopUsername();
    }
    
    @Override
    public String getEmail(List<Queue> queueList, int queueId) {
        String email="";
        for(Queue queue : queueList){
            if(queue.getId()==queueId)email=queue.getPopUsername();
        }
        return email;
    }

    

    

    
}
