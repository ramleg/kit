package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.model.beans.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.globant.corp.kit.service.EmailService;
import java.util.List;
import com.globant.corp.kit.repository.EmailRepository;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    EmailRepository repo;
    
    public List<Email> getByContent(String content){
        return repo.findByContent(content);
    }
    
    @Override
    public Email getOne(int id) {
        return null;
    }

    @Override
    public Iterable<Email> getAll() {
        return null;
    }

    @Override
    public void saveUnread(List<Email> kaceEmails) {
        
    }

    @Override
    public void saveOne(Email kaceEmail) {
        
    }
    
}
