package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.configuration.AppConfig;
import com.globant.corp.kit.entity.kace.TicketStatus;
import com.globant.corp.kit.repo.kace.TicketStatusRepo;
import com.globant.corp.kit.service.TicketStatusService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class TicketStatusServiceImpl implements TicketStatusService{
    
    @Autowired
    AppConfig config;
    
    @Autowired
    TicketStatusRepo repo;

    @Override
    public ArrayList<Integer> getApprovalStatusIds() {
        
        ArrayList<Integer> returnList = new ArrayList<>();
        for(TicketStatus ts : repo.findAllByDesc(config.getPendingApprovalState())){
            returnList.add(ts.getId());
        }
        return returnList;
    }
    
}
