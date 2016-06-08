package com.globant.corp.kgi.controller;

import com.globant.corp.kgi.model.beans.Cosa;
import com.globant.corp.kgi.serviceImpl.CosaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ramiro.acoglanis
 */
@RestController
@RequestMapping("/cosa")
public class CosaController {
    
    @Autowired
    CosaService service;
    
    @RequestMapping("/all")
    public Iterable<Cosa> getCosa(){
        return service.getAll();
    }
    
    @RequestMapping("/put/{description}/{foo}")
    public Iterable<Cosa> setCosa(@PathVariable String description,@PathVariable String foo){
        service.setCosa(new Cosa(description, foo));
        return service.getAll();
    }
}
