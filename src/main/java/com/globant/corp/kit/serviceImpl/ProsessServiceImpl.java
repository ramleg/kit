package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.model.beans.Email;
import com.globant.corp.kit.model.beans.KaceTicket;
import java.util.List;
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
    public void saveUnprocessedEmails (){
        Sort sort = new Sort(Sort.Direction.DESC,"uid");
        List<Email> storedEmails = emailRepo.findAll(sort);
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
        emailRepo.save(inboxService.getAll());
    }
    
    public List<KaceTicket> updateTickets(){
        List<Email> unprocessedEmails = emailRepo.findByProcessed(false);
        
        return null;
    }
    
    public List<KaceTicket> parseEmails(List<Email> unparsedEmails){
        
        return null;
    }
    
    
    
    
}
