package com.globant.corp.kit.controller;
import com.globant.corp.kit.entity.kace.KaceTicket;
import com.globant.corp.kit.entity.kit.Ticket;
import com.globant.corp.kit.service.KaceTicketService;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author ramiro.acoglanis
 */
@RestController
@RequestMapping("/controller")
public class MainController {
    
    
    @Autowired
    KaceTicketService kace;
    
        
    @RequestMapping("/all")
    public Iterable<KaceTicket> getAll(){
        return kace.getAll();
    }
    
    @RequestMapping("/save")
    public List<Ticket> saveUnreded(){
        return null;
    }
    
    @RequestMapping(value = "/filtro/{filtro}", method = RequestMethod.GET)
    public HashMap<String,String> getFiltered(@PathVariable("filtro") String filtro){
            return null;
    }
    
    @RequestMapping(value = "/test/{data}", method = RequestMethod.POST)
    public String test(@PathVariable("data") String data){
        return "ok";
    }
}