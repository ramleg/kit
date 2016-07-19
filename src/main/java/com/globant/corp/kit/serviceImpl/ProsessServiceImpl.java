package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.entity.kit.Email;
import com.globant.corp.kit.entity.kit.Ticket;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.globant.corp.kit.service.InboxService;
import org.springframework.data.domain.Sort;
import com.globant.corp.kit.service.ProsessService;
import com.globant.corp.kit.repo.kit.EmailRepository;
import com.globant.corp.kit.service.ParserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;// para consumir un servicio rest

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class ProsessServiceImpl implements ProsessService{
    
    @Autowired
    EmailRepository emailRepo;
    @Autowired
    ParserService parser;
    @Autowired
    InboxService inboxService;
    
    
    @Override
    @Transactional
    public void saveUnprocessedEmails (){
        Sort sort = new Sort(Sort.Direction.DESC,"uid");
        List<Email> storedEmails = emailRepo.findAll(sort);
        storedEmails = parser.normalizeEmail(storedEmails);
        if(!storedEmails.isEmpty()){
            Email lastEmailSaved = storedEmails.get(0);
            List<Email> unprocessedEmails = inboxService.getUnread(lastEmailSaved.getUid());
            unprocessedEmails.remove(0);
            emailRepo.save(unprocessedEmails);
        }else{
            saveAllEmails();
        }
    }

    @Override
    @Transactional
    public void saveAllEmails() {
        emailRepo.deleteAll();
        List<Email> unprocessedEmails = parser.normalizeEmail(inboxService.getAll());
        emailRepo.save(unprocessedEmails);
    }
        
    public void emailToTicket(){
        List<Email> unprocessedEmails = emailRepo.findByProcessed(false);
        
    }
}
