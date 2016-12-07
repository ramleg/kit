package com.globant.corp.kit.service;

import com.globant.corp.kit.entity.kace.Queue;
import java.util.List;

/**
 *
 * @author ramiro.acoglanis
 */
public interface QueueService {
    
    public List<Queue> getAllwedQueues();
    public List<Integer> getQueuesIds(List<Queue> queueList);
    public String getEmail(int ticketId);
    public String getEmail(List<Queue> queueList, int queueId);
    
}
