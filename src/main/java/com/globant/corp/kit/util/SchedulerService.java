package com.globant.corp.kit.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class SchedulerService {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Scheduled(fixedDelay = 30000)
    public void ProcessEmailToDataBase(){
        
        logger.info("Proccess Update");
        
    }
    
}
