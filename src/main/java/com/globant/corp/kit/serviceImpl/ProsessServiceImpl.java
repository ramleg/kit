package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.model.beans.Email;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.globant.corp.kit.service.InboxService;
import org.springframework.data.domain.Sort;
import com.globant.corp.kit.service.ProsessService;
import com.globant.corp.kit.repository.EmailRepository;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class ProsessServiceImpl implements ProsessService{
    
    @Autowired
    EmailRepository emailRepo;
    
    @Autowired
    InboxService inboxService;
    
    
    @Override
    @Transactional
    public List<Email> saveUnprocessedEmails (){
        Sort sort = new Sort(Sort.Direction.DESC,"uid");
        Email lastEmailSaved = emailRepo.findAll(sort).get(0);
        List<Email> unprocessedEmails = inboxService.getUnread(lastEmailSaved.getUid());
        unprocessedEmails.remove(0);
        emailRepo.save(unprocessedEmails);
        return unprocessedEmails;
    }

    @Override
    @Transactional
    public void processAll() {
        emailRepo.deleteAll();
        emailRepo.save(inboxService.getAll());
    }
    
}
