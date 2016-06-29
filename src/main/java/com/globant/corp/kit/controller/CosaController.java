package com.globant.corp.kit.controller;
import com.globant.corp.kit.model.beans.Approver;
import com.globant.corp.kit.model.beans.Comment;
import com.globant.corp.kit.model.beans.Email;
import com.globant.corp.kit.model.beans.Ticket;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.globant.corp.kit.service.EmailService;
import com.globant.corp.kit.service.InboxService;
import com.globant.corp.kit.service.ProsessService;
import org.springframework.web.bind.annotation.PathVariable;
import com.globant.corp.kit.repository.EmailRepository;
import com.globant.corp.kit.repository.TicketRepository;
import com.globant.corp.kit.service.RestConsumerService;
import com.globant.corp.kit.service.TicketService;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.web.bind.annotation.RequestMethod;

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
    
    @Autowired
    TicketService ticketService;
    
    @Autowired
    TicketRepository ticketRepo;
    
    @Autowired
    RestConsumerService rcs;
    
    @RequestMapping("/one")
    public Ticket getOne(){
        return ticketService.getById();
    }
    
    @RequestMapping("/all")
    public List<Ticket> getAll(){
        return ticketService.getAll();
    }
    
    @RequestMapping("/save")
    public List<Ticket> saveUnreded(){
        
        Ticket t = new Ticket();
        t.setId(1);
        t.setNum(55555);
        t.setTitle("yo mama");
        t.setSummary("yo mamma so fat...");
        t.setQueue("la re queue");
        t.setCreatedDate(new Date(123456789));
        t.setModifDate(new Date(123456789));
        t.setSubmitter("chavooon");
        t.setOwner("chipote");
        t.setStatus("waiting");
        t.setLocation("museion");
        t.setCategory1("cat1");
        t.setCategory2("cat2");
        t.setCategory3("cat3");
        t.setPriority("high");
        t.setProject("mopheusss");
        t.setUrl("www.yomamma.com");
        
        List<Comment> comments = new ArrayList<>();
        Comment com = new Comment();
        com.setComment("fucking comment");
        com.setDate(new Date(123456789));
        com.setWho("pinchilo");
        comments.add(com);
        
        List<Approver> approvers = new ArrayList<>();
        Approver app = new Approver();
        app.setState("approved");
        app.setApprover("fulano");
        approvers.add(app);
        
        t.setComments(comments);
        t.setApprovers(approvers);
        
        ticketRepo.save(t);
        return ticketService.getAll();
    }
    
    @RequestMapping(value = "/filtro/{filtro}", method = RequestMethod.GET)
    public String getFiltered(@PathVariable("filtro") String filtro){
        
        return rcs.postToGata(filtro);
    }
    
    @RequestMapping(value = "/test/{data}", method = RequestMethod.POST)
    public String test(@PathVariable("data") String data){
        
        return "putoooo";
    }
}