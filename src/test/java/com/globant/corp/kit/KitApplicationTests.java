package com.globant.corp.kit;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import com.globant.corp.kit.entity.kace.Ticket;
import com.globant.corp.kit.entity.local.ApprovalRequest;
import com.globant.corp.kit.repo.kace.TicketRepo;
import com.globant.corp.kit.repo.local.ApprovalRequestRepo;
import com.globant.corp.kit.service.ApprovalRequestService;
import com.globant.corp.kit.service.InboxService;
import com.globant.corp.kit.service.LdapService;
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
import com.globant.corp.kit.service.KGIService;
import java.util.Iterator;
import org.slf4j.LoggerFactory;

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
    KGIService kgi;
    
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
               
//        kgi.update();
    }


}
