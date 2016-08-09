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
import com.globant.corp.kit.service.RestConsumerService;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KitApplication.class)
@WebAppConfiguration
public class KitApplicationTests {

    @Autowired
    FullTicketService fullTicketService;
    
    @Autowired
    RestConsumerService rest;
    
        
        @Test
	public void contextLoads() {
            
        HashMap<String, String> body = new HashMap<>();
        body.put("title", "Ticket#50236");
        body.put("dueDate", "2016-08-01");
        body.put("approvalRequestTypeId", "2");
        body.put("approver", "fulanito");
        body.put("body", "<html><body></body></html>");
        body.put("author", "Fabio Olaechea");
        body.put("description", "Business Meals for Project Glow");
        body.put("language", "english");
        body.put("approveUrl", "https://gata.corp.globant.com/fake/approve-url");
        body.put("rejectUrl", "https://gata.corp.globant.com/fake/reject-url");
        
        System.out.println(rest.sendToGata(body).getBody().toString());
            
	}

}
