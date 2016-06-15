package com.globant.corp.kgi.schedul;

import com.globant.corp.kgi.service.KaceEmailService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author ramiro.acoglanis
 */
@Component
public class KaceScheduler {
    
    @Autowired
    KaceEmailService emailService;
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    public static String fecha;
    
    @Scheduled(fixedRate = 5000)
    public void getDate(){
        fecha = dateFormat.format(new Date());
    }
    
    @Scheduled(fixedDelay = 15000)
    public void readInbox(){
        
        
    }
    
}
