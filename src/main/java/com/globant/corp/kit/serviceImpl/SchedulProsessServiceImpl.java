package com.globant.corp.kit.serviceImpl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.globant.corp.kit.service.InboxService;
import com.globant.corp.kit.service.SchedulProsessService;
import com.globant.corp.kit.repository.EmailRepo;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class SchedulProsessServiceImpl implements SchedulProsessService{
    
    @Autowired
    EmailRepo emailRepo;
    
    @Autowired
    InboxService emailService;
    
    
    @Override
    public void processUnread (){
        
    }

    @Override
    public void processAll() {
    }
    
}
