package com.globant.corp.kgi.service;

import com.globant.corp.kgi.model.Cosa;
import com.globant.corp.kgi.repository.CosaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class CosaService {
    
    @Autowired
    CosaRepository repo;
    
    public Cosa getCosa(){
        return null;
    }
    
    public Iterable<Cosa> getAll(){
        return repo.findAll();
    }
    
    public void setCosa(Cosa cosa){
        repo.save(cosa);
    }
    
}
