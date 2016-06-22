package com.globant.corp.kit.controller;
import com.globant.corp.kit.model.beans.Email;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.globant.corp.kit.service.EmailService;

/**
 *
 * @author ramiro.acoglanis
 */
@RestController
@RequestMapping("/cosa")
public class CosaController {
    
    @Autowired
    EmailService emailService;
        
        
    @RequestMapping("/all")
    public List<Email> getCosa(){
        return emailService.getByContent("mundo");
    }
}