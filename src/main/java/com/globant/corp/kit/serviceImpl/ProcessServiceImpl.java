package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.configuration.AppConfig;
import com.globant.corp.kit.entity.kace.Queue;
import com.globant.corp.kit.entity.kace.Ticket;
import com.globant.corp.kit.exception.KaceMailingException;
import com.globant.corp.kit.exception.LdapContextException;
import com.globant.corp.kit.exception.NoApprovalRequestFoudException;
import com.globant.corp.kit.service.ApprovalRequestService;
import com.globant.corp.kit.service.InboxService;
import com.globant.corp.kit.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.globant.corp.kit.service.ProcessService;
import com.globant.corp.kit.service.QueueService;
import org.springframework.beans.factory.annotation.Value;
import com.globant.corp.kit.service.RestServiceConsumer;
import com.globant.corp.kit.service.TicketService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import javax.mail.Session;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class ProcessServiceImpl implements ProcessService{

    @Autowired
    AppConfig config;
    
    @Value("${kit.gata.url}")
    String gataUrl;
    
    
    String nl = System.getProperty("line.separator");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    TicketService ticketService;
    @Autowired
    ApprovalRequestService approvalService;
    @Autowired
    RestServiceConsumer restConsumer;
    @Autowired
    InboxService inbox;
    @Autowired
    LdapService ldap;
    @Autowired
    QueueService queueService;
    
    @Override
    @Transactional
    public boolean updateGata(){
        
        Session smtpSession = inbox.getSession();
        List<Queue> queueList = queueService.getAllwedQueues();
        List<Integer> queuesIds = queueService.getQueuesIds(queueList);
        List<Ticket> pendingApprovalTickets = ticketService.getPendingApprovalTicketsList(queuesIds);
        // Comienza el proceso siempre que haya tickets con approvals pendientes
        if(!pendingApprovalTickets.isEmpty()){
            try{
            DirContext ctx = ldap.getContext();
            logger.info("-->> Update Proccess Started!!");
            // Itero la lista de ticket extraidos de la DB de Kace
            for(Ticket ticket : pendingApprovalTickets){
                // Itero cada ticket segun la cantidad de approvers que tiene
                for(String approver : ticket.getApproversList()){
                    // Valido individualmente cada approver antes de ejecutar el proceso
                    if(ldap.validateUser(approver, ctx)){
                        // si la combinacion de num de ticket y approver existe en la DB local, 
                        // significa que que el request ya se proceso y esta a la espera de aprobacion
                        if(!approvalService.exist(ticket.getId(), approver)){
                            // las validaciones son positivas por lo que agrego el request a la lista
                            ticket.addApprovalRequest(approver);
                        }else{
                            logger.info("The request: [" + ticket.getId() + "/" + approver + "] was allready procesed. Waiting for response.");
                            ticket.setAllowClean(true);
                        }
                    }else{
                        logger.info("Invalid User - [TICK:" + ticket.getId() + " | APPROVER:'" + approver + "']");
                        ticket.addValidationErrors("Approver Validation Error: '" + approver + "' doesn't exist");
                    }
                }
                
                List<String> approvalRequest = ticket.getApprovalRequest();
                List<String> validationError = ticket.getValidationErrors();
                // si luego de las validaciones tengo Request, Errores o hay que limpiar el ticket, envio el mail correspondiente a Kace
                if(!approvalRequest.isEmpty() || !validationError.isEmpty() || ticket.isAllowClean()){
                    //envia mail a kace para actualizar el ticket
                    String emailTo = queueService.getEmail(queueList, ticket.getQueueId());
                    if(sendUpdateToKace(ticket, emailTo, smtpSession)){
                        logger.info("Kace Updated via email - [TICK:" + ticket.getId() + "]");
                        // si el envio del mail es true, envio los request a gata y guardo en local DB
                        for(String approver: approvalRequest){
                            // post to gata
                            if(postToGATA(ticket, approver)){
                                logger.info("GATA received the request: [TICK:" + ticket.getId() + " | APPROVER:'" + approver + "']");
                                // guarda en local DB el request
                                approvalService.save(ticket.getId(), approver);
                                logger.info("Request saved in local DB: [TICK:" + ticket.getId() + " | APPROVER:'" + approver + "']");
                            }
                        }
                    }
                }
            }
            ctx.close();
            logger.info("Update Proccess Finished!!");
            return true;
            }catch(LdapContextException e){
                logger.error("LDAP Service - Can't get 'DirContext'");
                return false;
            }catch(NamingException e){
                logger.info("Couldn't close Ldap Context");
                return true;
            }catch(Exception e){
                logger.error("Unexpected error - Update process ended");
                return false;
            }
        }else{
            return false;
        }
        
    }
    
    
    private boolean postToGATA(Ticket ticket, String approver){
        
        HashMap<String, String> body = new HashMap<>();
            body.put("title", "Approval Request for Ticket#" + ticket.getId());
            body.put("dueDate",dueDate(30));
            body.put("approvalRequestTypeId", "34");
            body.put("approver", approver);
            body.put("body", "<html><body>bodyyyy</body></html>");
            body.put("author", ticket.getSubmitter().getUserName());
            body.put("description", ticket.getSummary());
            body.put("language", "english");
            body.put("approveUrl", config.getApprovalURL() + ticket.getId() + "/" + approver + "/");
            body.put("rejectUrl", config.getRejectURL() + ticket.getId() + "/" + approver + "/");

            ResponseEntity response = restConsumer.postToGata(body);
            if(response.getStatusCodeValue()==201){
                return true;
            }else{
                logger.error("while sending data to GATA - Ticket#" + ticket.getId() + " - Arrover:'" + approver + "'");
                return false;
            }
    }
    
    private String dueDate(int due){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(sdf.format(new Date())));
            c.add(Calendar.DATE, due);
            return sdf.format(c.getTime());
        } catch (ParseException ex) {
            logger.error("while seting up the DueDate");
            return "2099-12-01";
        }
    }
    
    private boolean sendUpdateToKace(Ticket ticket,String emailTo,  Session smtpSession){
        
        String subject = "TICK:" + ticket.getId();
        
        String content = 
                "@cc_list=" + getActualCcList(ticket) + nl +
                "@custom_" + config.getApproversField() + "=" + nl;
        
        if(ticket.getApprovalRequest().isEmpty() && !ticket.getValidationErrors().isEmpty()){
            content = content + "@status=" + config.getApprovedState() + nl;
        }
        
        if(!ticket.getApprovalRequest().isEmpty()){
            content = content +
                "## KGI Messege: ..." + nl +
                "## The approval request was sent to GATA for the user/s: ";
                for(String approver: ticket. getApprovalRequest())content = content + "[" + approver + "] ";
            content = content + nl +
                "## waiting for response... ";
        }
        
        if(!ticket.getValidationErrors().isEmpty()){
            content = content + nl + nl + "## Errors Found: ...";
            for(String error : ticket.getValidationErrors()){
                content = content + nl + 
                        "## [" + error + "]";
            }
        }
                
        try {
            inbox.Send(subject, content, emailTo, smtpSession);
            return true;
        } catch (KaceMailingException ex) {
            logger.error("While sending email to KACE - [TICK:" + ticket.getId() + "]");
            return false;
        }
    }
    
    private String getActualCcList(Ticket ticket){
        String cclist = ticket.getCcList();
        for(String approver : ticket.getApprovalRequest()){
            if(!cclist.contains(approver)){
                if(cclist.equals("")){
                    cclist = cclist + approver + "@globant.com";
                }else{
                    cclist = cclist + ", " + approver + "@globant.com";
                }
            }
        }
        return cclist;
    }
    
    @Override
    public HashMap<String,String> kgiEndPoint(String isApproved, String ticketNum, String approver, String comment) {
        
        // send email to kace to update the ticket
        HashMap<String,String> map = new HashMap<>();
        try{
            sendApprovalToKace(isApproved, ticketNum, approver, comment);
            approvalService.delete(Integer.parseInt(ticketNum), approver);
            map.put("status", "yes".equals(isApproved)?"Approved":"Rejected");
            map.put("Description", "The ticket#" + ticketNum + " was processed");            
        }catch(KaceMailingException |NoApprovalRequestFoudException e ){
            map.put("status", "Error");
            map.put("Description", e.getMessage() + " [TICK:" + ticketNum + " | APPROVER:'" + approver + "']");
            logger.error("** Wile sending approval to Kace via mail [TICK:" + ticketNum + " | APPROVER:'" + approver + "']");
        } 
        return map;
    }
    
    
    private void sendApprovalToKace(String isApproved, String ticketNum, String approver, String comment) throws KaceMailingException, NoApprovalRequestFoudException{
        
        if(approvalService.exist(Integer.parseInt(ticketNum), approver)){
            String queueEmail = queueService.getEmail(Integer.parseInt(ticketNum));
            
            String approvalStatus = "yes".equals(isApproved)?"APPROVED":"REJECTED";
            
            String ticketStatus = config.getApprovedState();
            if(approvalService.getByTicketNum(Integer.parseInt(ticketNum)).size() > 1)ticketStatus = config.getPendingApprovalState();
            
            String additionalCommnets = "";
            if(!comment.equals(""))additionalCommnets = "##" + nl + "## Aditional comments: '" + comment + "'";

            String subject = "TICK:" + ticketNum;
            String content = 
                    "@status=" + ticketStatus + nl + 
                    "## KGI Messege: ..." + nl +
                    "## The user '" + approver + "' - " + approvalStatus + " - the request. " + nl + additionalCommnets;
            inbox.Send(subject, content, queueEmail, inbox.getSession());
            logger.info("** The User: '" + approver + "' - " + approvalStatus + " the request for the TICK:" + ticketNum);
        }else{
            logger.error("** Request not found in local DB [TICK:" + ticketNum + " | APPROVER:'" + approver + "']");
            throw new NoApprovalRequestFoudException("Approval Request not found");
        }
        
            
            
        
        
    }
    
    
    

    
}
