package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.model.beans.KaceEmail;
import com.globant.corp.kit.repository.KaceEmailRepository;
import com.globant.corp.kit.service.KaceEmailService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class KaceEmailServiceImpl implements KaceEmailService{

    @Autowired
    KaceEmailRepository repo;
    
    @Override
    public KaceEmail getOne(int id) {
        return repo.findOne(id);
    }

    @Override
    public Iterable<KaceEmail> getAll() {
        return repo.findAll();
    }

    @Override
    public void saveUnread(Iterable<KaceEmail> kaceEmails) {
        repo.save(kaceEmails);
    }

    @Override
    public void saveOne(KaceEmail kaceEmail) {
        repo.save(kaceEmail);
    }
    
}
