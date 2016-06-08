package com.globant.corp.kgi.serviceImpl;

import com.globant.corp.kgi.model.beans.KaceEmail;
import com.globant.corp.kgi.repositoryImpl.KaceEmailRepoImpl;
import com.globant.corp.kgi.service.KaceEmailService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class KaceEmailServiceImpl implements KaceEmailService{


    @Override
    public ArrayList<KaceEmail> getNewOnes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<KaceEmail> getAll() {
        return new KaceEmailRepoImpl().getAll("TICKETS");
    }

    @Override
    public KaceEmail getOne() {
        return new KaceEmailRepoImpl().getOne("TICKETS");
    }
}
