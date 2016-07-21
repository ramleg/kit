package com.globant.corp.kit.controller;
import com.globant.corp.kit.entity.kace.MiniTicket;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import com.globant.corp.kit.service.MiniTicketService;
import com.globant.corp.kit.service.ProcessService;

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
    
        
    @RequestMapping("/all")
    public Iterable<MiniTicket> getAll(){
        return kace.getAll();
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
}