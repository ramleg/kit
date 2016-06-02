package com.globant.corp.kgi;

import com.globant.corp.kgi.repository.CosaRepository;
import com.globant.corp.kgi.model.*;
import com.globant.corp.kgi.configuration.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
import java.math.BigDecimal;
 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 *
 * @author ramiro.acoglanis
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class CosaRepositoryTest {
    
    @Autowired
    CosaRepository repo;
    
    @Test
    public void testSaveCosa(){
        
        
        Cosa cosa = new Cosa("toda cosa","fafafa");
        
        repo.save(cosa);
        
    }
    
}
