package com.globant.corp.kit;

import com.globant.corp.kit.entity.kace.KaceTicket;
import com.globant.corp.kit.service.KaceTicketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KitApplication.class)
@WebAppConfiguration
public class KitApplicationTests {

    @Autowired
    KaceTicketService kace;
    
	@Test
	public void contextLoads() {
            
            KaceTicket ticket = new KaceTicket();
            ticket.setId(5);
            ticket.setName("chipotito");
            kace.save(ticket);
            Iterable<KaceTicket> it = kace.getAll();
            
            for(KaceTicket k: it){
                System.out.println(k.getId() + " - - - " + k.getName());
            }
	}

}
