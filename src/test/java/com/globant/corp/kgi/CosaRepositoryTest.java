package com.globant.corp.kgi;

import com.globant.corp.kgi.repository.CosaRepository;
import com.globant.corp.kgi.configuration.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
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
        
        
//        Cosa cosa = new Cosa("toda cosa","fafafa");
//        
//        // Save
//        assertNull(cosa.getId()); //null before save
//        repo.save(cosa);
//        assertNotNull(cosa.getId()); //not null after save
//        
//        // Fetch from DB
//        Cosa fetchedCosa = repo.findOne(cosa.getId());
//        
//        //should not be null
//        assertNotNull(fetchedCosa);
//        
//        //should equal
//        assertEquals(cosa.getId(), fetchedCosa.getId());
//        assertEquals(cosa.getDescription(), fetchedCosa.getDescription());
//        
//        //update description and save
//        fetchedCosa.setDescription("New Description");
//        repo.save(fetchedCosa);
//        
//        //get from DB, should be updated
//        Cosa fetchedUpdatedProduct = repo.findOne(fetchedCosa.getId());
//        assertEquals(fetchedCosa.getDescription(), fetchedUpdatedProduct.getDescription());
//        
//        //verify count of products in DB
//        long productCount = repo.count();
//        int x = 1;
//        assertEquals(productCount, x);
//        
//        //get all products, list should have "x" elements
//        Iterable<Cosa> products = repo.findAll();
//        
//        int count = 0;
// 
//        for(Cosa c : products){
//            count++;
//        }
// 
//        assertEquals(count, x);
    }
    
}
