package com.globant.corp.kit.controller;

import com.globant.corp.kit.model.beans.Cosa;
import com.globant.corp.kit.model.beans.KaceEmail;
import com.globant.corp.kit.service.EmailService;
import com.globant.corp.kit.serviceImpl.CosaService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ramiro.acoglanis
 */
@RestController
@RequestMapping("/cosa")
public class CosaController {
    
    @Autowired
    CosaService service;
    
    @Autowired
    EmailService emails;
    
    @RequestMapping("/all")
    public ArrayList<KaceEmail> getCosa(){
        return emails.getUnread(5);
    }
    
    @RequestMapping("/put/{description}/{foo}")
    public Iterable<Cosa> setCosa(@PathVariable String description,@PathVariable String foo){
        service.setCosa(new Cosa(description, foo));
        return service.getAll();
    }
    
    @RequestMapping(value = "/laposta/{hola}", method = RequestMethod.POST)
    public String laposta(@PathVariable String hola){
        RestTemplate f = new RestTemplate();
        return hola;
    }
    
}
