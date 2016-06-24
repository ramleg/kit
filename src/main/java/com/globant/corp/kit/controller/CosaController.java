package com.globant.corp.kit.controller;
import com.globant.corp.kit.model.beans.Email;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.globant.corp.kit.service.EmailService;
import com.globant.corp.kit.service.InboxService;
import com.globant.corp.kit.service.ProsessService;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import com.globant.corp.kit.repository.EmailRepository;

/**
 *
 * @author ramiro.acoglanis
 */
@RestController
@RequestMapping("/cosa")
public class CosaController {
    
    @Autowired
    EmailService emailService;
    
    @Autowired
    InboxService inboxService;
    
    @Autowired
    EmailRepository emailRepo;
    
    @Autowired
    ProsessService processService;
    
    @RequestMapping("/one")
    public Email getOne(){
        Sort sort = new Sort(Sort.Direction.DESC,"uid");
        Email lastEmailSaved = emailRepo.findAll(sort).get(0);
        return lastEmailSaved;
    }
    
    @RequestMapping("/all")
    public List<Email> getAll(){
        Sort sort = new Sort(Sort.Direction.ASC,"uid");
        return emailRepo.findByProcessedOrderByUidDesc(false);
    }
    
    @RequestMapping("/save")
    public String saveUnreded(){
        processService.saveUnprocessedEmails();
        return "ok";
    }
    
    @RequestMapping("/filtro/{filtro}")
    public List<Email> getFiltered(@PathVariable("filtro") String filtro){
        
        return null;
    }
    
    @RequestMapping("/test")
    public String test(){
        emailRepo.markProcessedEmails();
        return "ok";
    }
}