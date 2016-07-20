package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.entity.kace.MiniTicket;
import com.globant.corp.kit.entity.kit.TicketReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.globant.corp.kit.service.InboxService;
import com.globant.corp.kit.repo.kit.EmailRepository;
import com.globant.corp.kit.service.MiniTicketService;
import com.globant.corp.kit.service.TicketRegService;
import java.util.ArrayList;
import java.util.List;
import com.globant.corp.kit.service.ProcessService;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class ProcessServiceImpl implements ProcessService{

    @Autowired
    MiniTicketService miniTicketService;
    
    @Autowired
    TicketRegService ticketRegService;
    
    @Override
    public void rebuildRegistredTickets() {
        ticketRegService.clean();
        ArrayList<TicketReg> ticketRegList = new ArrayList<>();
        
        for(MiniTicket mini : miniTicketService.getAll()){
            TicketReg ticketReg = new TicketReg();
            ticketReg.setId(mini.getId());
            ticketReg.setModified(mini.getModified());
            ticketRegList.add(ticketReg);
        }
        
        ticketRegService.save(ticketRegList);
        
    }
    
}
