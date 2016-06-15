package com.globant.corp.kgi.serviceImpl;

import com.globant.corp.kgi.model.beans.KaceEmail;
import com.globant.corp.kgi.model.beans.KaceTicket;
import com.globant.corp.kgi.repository.KaceEmailRepo;
import com.globant.corp.kgi.repositoryImpl.KaceEmailRepoImpl;
import com.globant.corp.kgi.service.KaceEmailService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.Ticket;
/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class KaceEmailServiceImpl implements KaceEmailService{

    @Autowired
    KaceEmailRepo repo;
    
    @Override
    public ArrayList<KaceEmail> getNewOnes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<KaceEmail> getAll() {
        return repo.getAll();
    }

    @Override
    public KaceEmail getOne() {
        return repo.getOne();
    }

    @Override
    public String sendApproval() {
        return repo.SendAproval(new KaceTicket());
    }
}
