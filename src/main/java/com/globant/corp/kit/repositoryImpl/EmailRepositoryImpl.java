package com.globant.corp.kit.repositoryImpl;

import com.globant.corp.kit.model.beans.Email;
import com.globant.corp.kit.repository.EmailRepositoryCustom;
import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author ramiro.acoglanis
 */
@Repository
@Transactional
@EnableTransactionManagement
public class EmailRepositoryImpl implements EmailRepositoryCustom{
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public List<Email> someMethod(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Email").list();
    }
    
    
    
    
}
