package com.globant.corp.kit.repositoryImpl;

import com.globant.corp.kit.model.beans.Email;
import java.util.List;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import com.globant.corp.kit.repository.EmailRepo;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */
//@Repository
//public class EmailRepoImpl extends HibernateDaoSupport{
public class EmailRepoImpl {
    
//    public EmailRepoImpl(SessionFactory sessionFactory) {
//        setSessionFactory(sessionFactory);
//    }
    
    public List<Email> getAll(){
//      session = getSessionFactory().getCurrentSession();
        //return getSessionFactory().getCurrentSession().createQuery("from Email").list();
        return null;
    }
}
