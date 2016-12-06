package com.globant.corp.kit.controller;
import com.globant.corp.kit.entity.kace.Ticket;
import com.globant.corp.kit.service.InboxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import com.globant.corp.kit.service.ProcessService;
import java.util.List;
import com.globant.corp.kit.service.RestServiceConsumer;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author ramiro.acoglanis
 */
@RestController
@RequestMapping("/controller")
public class MainController {
    
    @Autowired
    ProcessService process;
    @Autowired
    InboxService inbox;
    @Autowired
    RestServiceConsumer rest;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @RequestMapping("/hola")
    public String hola(){
        return "hola mondooo";
    }
        
    @RequestMapping("/update")
    public boolean getModified(){
        return process.updateGata();
    }
    
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String postApproval(@RequestBody Map<String, String> gata){
        //return process.kgiEndPoint(gata.get("isApproved"), gata.get("ticketNum"), gata.get("approver"), gata.get("comment"));
        return null;
    }
    
    @RequestMapping(value = "/approval/{isApproved}/{ticketNum}/{approver}/{comment}", method = RequestMethod.GET)
    public ResponseEntity<Map> sendApproval(@PathVariable("isApproved") String isApproved, @PathVariable("ticketNum") String ticketNum, @PathVariable("approver") String approver, @PathVariable("comment") String comment ){
        return new ResponseEntity<Map>(process.kgiEndPoint(isApproved, ticketNum, approver, comment), HttpStatus.OK);
    }
       
}