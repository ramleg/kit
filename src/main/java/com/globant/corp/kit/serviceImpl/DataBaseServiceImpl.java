package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.repository.KaceEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class DataBaseServiceImpl {
    
    @Autowired
    KaceEmailRepository repo;
    
    public void hqlTruncate(){
        repo.deleteAll();
    }
}
