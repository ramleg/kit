package com.globant.corp.kit.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.globant.corp.kit.service.EmailService;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class SchedulerService {
         
    @Scheduled(fixedDelay = 15000)
    public void ProcessEmailToDataBase(){
        
        
    }
    
}
