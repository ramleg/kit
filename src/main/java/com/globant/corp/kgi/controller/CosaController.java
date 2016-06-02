package com.globant.corp.kgi.controller;

import com.globant.corp.kgi.model.Cosa;
import com.globant.corp.kgi.service.CosaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    CosaService cs;
    
    @RequestMapping("/all")
    public Iterable<Cosa> getCosa(){
        return cs.getAll();
    }
    
    @RequestMapping("/put")
    public String setCosa(){
        cs.setCosa(new Cosa("hola", "mundo"));
        
        return "ok";
    }
}
