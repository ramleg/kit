package com.globant.corp.kit;

import com.globant.corp.kit.entity.kace.Ticket;
import com.globant.corp.kit.entity.local.ApprovalRequest;
import com.globant.corp.kit.repo.kace.TicketRepo;
import com.globant.corp.kit.repo.local.ApprovalRequestRepo;
import com.globant.corp.kit.service.ApprovalRequestService;
import com.globant.corp.kit.service.InboxService;
import com.globant.corp.kit.service.LdapService;
import com.globant.corp.kit.service.ProcessService;
import com.globant.corp.kit.service.QueueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.util.HashMap;
import com.globant.corp.kit.service.RestServiceConsumer;
import com.globant.corp.kit.service.TicketService;
import com.globant.corp.kit.service.TicketStatusService;
import com.globant.corp.kit.util.LogReader;
import java.text.ParseException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KitApplication.class)
@WebAppConfiguration
public class KitApplicationTests {

    @Autowired
    TicketService ticketService;
    
    @Autowired
    RestServiceConsumer rest;
    
    @Autowired
    TicketRepo ticketRepo;
    
    @Autowired
    InboxService inbox;
    
    @Autowired
    ProcessService prs;
    
    @Autowired
    TicketStatusService tss;

    @Autowired
    ApprovalRequestRepo aprRepo;
    
    @Autowired
    LdapService ldap;

    @Autowired
    QueueService queueService;

    @Test
    public void contextLoads() throws ParseException {
//        prs.updateGata();
    }


}
