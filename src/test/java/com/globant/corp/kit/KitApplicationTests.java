package com.globant.corp.kit;

import com.globant.corp.kit.entity.kace.FullTicket;
import com.globant.corp.kit.entity.kace.MiniTicket;
import com.globant.corp.kit.service.FullTicketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.globant.corp.kit.service.MiniTicketService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KitApplication.class)
@WebAppConfiguration
public class KitApplicationTests {

    @Autowired
    FullTicketService fullTicketService;
    
//	@Test
//	public void contextLoads() {
//            
//            Iterable<FullTicket> i = fullTicketService.getAll();
//            
//            System.out.println("******----*******----******----*******----******----*******----******----*******----");
//            for(FullTicket k: i){
//                System.out.println("*** -> " + k.getId() + " - - - " + k.getTitle() + " - - -   {" + k.getHistoryString() + "}");
//            }
//            System.out.println("******----*******----******----*******----******----*******----******----*******----");
//	}
        
        @Test
	public void contextLoads() {
//            FullTicket ft = fullTicketService.getById(38);
//            
//            System.out.println("******----*******----******----*******----******----*******----******----*******----");
//            System.out.println("******----  " + ft.getId() + " -- " + ft.getSummary());
//            System.out.println("******----*******----******----*******----******----*******----******----*******----");
            
	}

}
