package com.globant.corp.kit.controller;
import com.globant.corp.kit.entity.kace.Ticket;
import com.globant.corp.kit.service.InboxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;
import com.globant.corp.kit.service.RestServiceConsumer;
import com.globant.corp.kit.util.LogReader;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import com.globant.corp.kit.service.KGIService;

/**
 *
 * @author ramiro.acoglanis
 */
@RestController
@RequestMapping("/kgi")
public class MainController {
    
    @Autowired
    KGIService kgi;
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
        return kgi.update();
    }
    
    @RequestMapping(value = "/log", produces = "text/html; charset=utf-8")
    public String getLog() {
        LogReader reader = new LogReader();
        String param = "";
        return reader.getLog(param);
    }
    @RequestMapping(value = "/log/{param}", produces = "text/html; charset=utf-8")
    public String getArchivedLog(@PathVariable("param") String param) {
        LogReader reader = new LogReader();
        return reader.getLog(param);
    }
    
    @RequestMapping(value = "/a/{requestID}/{token}", method = RequestMethod.GET)
    public ResponseEntity<Map> approve(@PathVariable("requestID") String requestID, @PathVariable("token") String token){
        
        if(token.equals("t")){
            return new ResponseEntity<Map>(kgi.kgiEndPoint("Approved", requestID,"", "token"), HttpStatus.OK);
        }else{
            return null;
        }
    }
    
    @RequestMapping(value = "/r/{requestID}/{comment}/{token}", method = RequestMethod.GET)
    public ResponseEntity<Map> reject(@PathVariable("requestID") String requestID, @PathVariable("comment") String comment, @PathVariable("token") String token){
        
        if(token.equals("t")){
            return new ResponseEntity<Map>(kgi.kgiEndPoint("Rejected", requestID, comment, "token"), HttpStatus.OK);
        }else{
            return null;
        }        
    }
    
    
//    @RequestMapping(value = "/post", method = RequestMethod.POST)
//    public String postApproval(@RequestBody Map<String, String> gata){
//        //return kgi.kgiEndPoint(gata.get("isApproved"), gata.get("ticketNum"), gata.get("approver"), gata.get("comment"));
//        return null;
//    }
    
//    @RequestMapping(value = "/approval/{isApproved}/{ticketNum}/{approver}/{comment}", method = RequestMethod.GET)
//    public ResponseEntity<Map> sendApprovalWithComment(@PathVariable("isApproved") String isApproved, @PathVariable("ticketNum") String ticketNum, @PathVariable("approver") String approver, @PathVariable("comment") String comment ){
//        return new ResponseEntity<Map>(kgi.kgiEndPoint(isApproved, ticketNum, approver, comment), HttpStatus.OK);
//    }

//    @RequestMapping(value = "/approval/{isApproved}/{ticketNum}/{approver}", method = RequestMethod.GET)
//    public ResponseEntity<Map> sendApprovalNoComment(@PathVariable("isApproved") String isApproved, @PathVariable("ticketNum") String ticketNum, @PathVariable("approver") String approver){
//        String comment = "";
//        return new ResponseEntity<Map>(kgi.kgiEndPoint(isApproved, ticketNum, approver, comment), HttpStatus.OK);
//    }
    
}