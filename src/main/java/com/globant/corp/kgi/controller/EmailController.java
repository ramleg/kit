package com.globant.corp.kgi.controller;

import com.globant.corp.kgi.model.Email;
import com.globant.corp.kgi.service.EmailService;
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
public class EmailController {
    
    @Autowired
    EmailService service;
    
    @RequestMapping("/read/last")
    public Email readLast(){
        return new EmailService().readInbox();
    }
    @RequestMapping("/read/all")
    public Email readAll(){
        return new EmailService().readInbox();
    }
}
