package com.globant.corp.kgi.controller;

import com.globant.corp.kgi.model.beans.KaceEmail;
import com.globant.corp.kgi.service.KaceEmailService;
import com.globant.corp.kgi.serviceImpl.KaceEmailServiceImpl;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ramiro.acoglanis
 */
@RestController
@RequestMapping("/email")
public class KaceEmailController {
    
    @Autowired
    private KaceEmailService service;
    
    @RequestMapping("/read/last")
    public KaceEmail readLast(){
        return service.getOne();
    }
    @RequestMapping("/read/all")
    public ArrayList<KaceEmail> readAll(){
        return service.getAll();
    }
    @RequestMapping("/send")
    public String send(){
        return service.sendApproval();
    }
}