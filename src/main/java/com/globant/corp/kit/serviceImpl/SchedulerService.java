package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.service.KaceEmailService;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    
    
    @Scheduled(fixedDelay = 15000)
    public void ProcessEmailToDataBase(){
        
        
    }
    
}
