package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.entity.kace.Queue;
import com.globant.corp.kit.repo.kace.QueueRepo;
import com.globant.corp.kit.service.QueueService;
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
    
    @Override
    public List<Integer> getQueuesIds() {
        Iterable<Queue> queueList = repo.findByAllowed(1);
        List<Integer> returnList = new ArrayList<>();
        for(Queue queue : queueList){
            returnList.add(queue.getId());
        }
        return  returnList;
    }
    
}
