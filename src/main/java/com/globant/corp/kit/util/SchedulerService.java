package com.globant.corp.kit.util;

import com.globant.corp.kit.service.ProcessService;
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
    
    @Autowired
    ProcessService prs;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Scheduled(fixedDelay = 120000)
    public void systemUpdate(){
        prs.updateGata();
    }
    
}
