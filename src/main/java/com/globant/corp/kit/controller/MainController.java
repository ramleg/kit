package com.globant.corp.kit.controller;
import com.globant.corp.kit.entity.local.Email;
import com.globant.corp.kit.service.InboxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import com.globant.corp.kit.service.MiniTicketService;
import com.globant.corp.kit.service.ProcessService;
import com.globant.corp.kit.service.RestConsumerService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author ramiro.acoglanis
 */
@RestController
@RequestMapping("/controller")
public class MainController {
    
    
    @Autowired
    MiniTicketService kace;
    
    @Autowired
    ProcessService process;
    
    @Autowired
    InboxService inbox;
    
    @Autowired
    RestConsumerService rest;
        
    @RequestMapping("/all")
    public ArrayList<Email> getAll(){
        return inbox.getAll();
    }
    
    @RequestMapping(value = "/send/{num}/{approver}/{comment}", method = RequestMethod.GET)
    public String sendApproval(@PathVariable("num") String num, @PathVariable("approver") String approver, @PathVariable("comment") String comment ){
        return inbox.Send(num, approver, comment);
    }
    
    @RequestMapping("/clean")
    public String clean(){
        process.rebuildRegistredTickets();
        return "ok";
    }
    
    @RequestMapping(value = "/filtro/{filtro}", method = RequestMethod.GET)
    public HashMap<String,String> getFiltered(@PathVariable("filtro") String filtro){
            return null;
    }
    
    @RequestMapping(value = "/test/{data}", method = RequestMethod.POST)
    public String test(@PathVariable("data") String data){
        return data;
    }
    
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String postToGata(){
        
        HashMap<String, String> body = new HashMap<>();
        body.put("title", "Ticket#50236");
        body.put("dueDate", "2016-08-01");
        body.put("approvalRequestTypeId", "2");
        body.put("approver", "fulanito");
        body.put("body", "<html><body></body></html>");
        body.put("author", "Fabio Olaechea");
        body.put("description", "Business Meals for Project Glow");
        body.put("language", "english");
        body.put("approveUrl", "https://gata.corp.globant.com/fake/approve-url");
        body.put("rejectUrl", "https://gata.corp.globant.com/fake/reject-url");
                
        return rest.postToGata(body).getBody().toString();
    }
    
}