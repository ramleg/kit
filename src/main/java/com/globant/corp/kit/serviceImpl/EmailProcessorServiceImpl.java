package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.repository.KaceEmailRepository;
import com.globant.corp.kit.service.EmailProcessorService;
import com.globant.corp.kit.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class EmailProcessorServiceImpl implements EmailProcessorService{
    
    @Autowired
    KaceEmailRepository kaceEmailRepo;
    @Autowired
    EmailService emailService;
    
    @Override
    public void processUnread (){
        
    }

    @Override
    public void processAll() {
        kaceEmailRepo.deleteAll();
        kaceEmailRepo.save(emailService.getAll());
    }
    
}
