package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.entity.kace.MiniTicket;
import com.globant.corp.kit.entity.local.TicketReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.globant.corp.kit.service.MiniTicketService;
import com.globant.corp.kit.service.TicketRegService;
import java.util.ArrayList;
import java.util.List;
import com.globant.corp.kit.service.ProcessService;
import com.globant.corp.kit.service.RestConsumerService;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class ProcessServiceImpl implements ProcessService{

    @Value("${kit.application.allow-rebuild}")
    String allowRebuild;
    @Value("${kit.gata.url}")
    String gataUrl;
    @Autowired
    MiniTicketService miniTicketService;
    @Autowired
    TicketRegService ticketRegService;
    @Autowired
    RestConsumerService restConsumer;
    
    
    @Override
    public void systemUpdate(){
        
        List<MiniTicket> updatedTickets = getModifiedMiniTickets();
        
        
    }
    
    
    @Override
    public String rebuildRegistredTickets() {
        
        if(allowRebuild.equals("true")){
            ticketRegService.clean();
            ArrayList<TicketReg> ticketRegList = new ArrayList<>();
            for(MiniTicket mini : miniTicketService.getAll()){
                TicketReg ticketReg = new TicketReg();
                ticketReg.setId(mini.getId());
                ticketReg.setModified(mini.getModified());
                ticketRegList.add(ticketReg);
            }
            ticketRegService.save(ticketRegList);
            return "The DataBase has been rebuilded";
        }else{
            return "Rebuild is not allowed";
        }
    }
    
    
    private List<MiniTicket> getModifiedMiniTickets(){
        HashMap<Integer, MiniTicket> miniTicketMap = miniTicketService.getAllHashMap();
        HashMap<Integer, TicketReg> ticketRegMap = ticketRegService.getAllHashMap();
        ArrayList<MiniTicket> miniTicketList = new ArrayList<>();
        for(Entry<Integer, MiniTicket> entry : miniTicketMap.entrySet()){
            Integer key = entry.getKey();
            MiniTicket mini = entry.getValue();
            TicketReg reg = ticketRegMap.get(key);
            if(Objects.isNull(reg)){
                miniTicketList.add(mini);
            }else{
                if(mini.getModified().compareTo(reg.getModified()) != 0){
                    miniTicketList.add(mini);
                }
            }
        }
        return miniTicketList;
    }
    
    
}
