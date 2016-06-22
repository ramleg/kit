package com.globant.corp.kit.repositoryImpl;

import com.globant.corp.kit.model.beans.Email;
import com.globant.corp.kit.repository.EmailRepositoryCustom;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
/**
 *
 * @author ramiro.acoglanis
 */
@Repository
public class EmailRepositoryImpl implements EmailRepositoryCustom{

    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public void setSessionFactory(SessionFactory sessionFactory) {
         this.sessionFactory = sessionFactory;
    }
    
    @Override
    public List<Email> someMethod(){
//        Session session = getSessionFactory().getCurrentSession();
//        return session.createQuery("from Email").list();
        System.out.println(getSessionFactory().toString());
        return new ArrayList<Email>();
    }
    
}
