package com.globant.corp.kit.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.globant.corp.kit.service.KGIService;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class SchedulerService {
    
    @Autowired
    KGIService kgi;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Scheduled(fixedDelay = 120000)
    public void systemUpdate(){
        //kgi.update();
    }
    
}
