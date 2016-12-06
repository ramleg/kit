package com.globant.corp.kit.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class SchedulerService {
         
    @Scheduled(fixedDelay = 30000)
    public void ProcessEmailToDataBase(){
        
        
        
    }
    
}
