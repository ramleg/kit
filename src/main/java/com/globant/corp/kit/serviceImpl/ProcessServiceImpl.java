package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.configuration.AppConfig;
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
    
    @Override
    @Transactional
    public boolean updateGata(){
        
        Session smtpSession = inbox.getSession();
        
        try{
            DirContext ctx = ldap.getContext();
            // Itero la lista de ticket extraidos de la DB de Kace
            for(Ticket ticket : ticketService.getPendingApprovalTicketsList()){
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
                            logger.error("INFO: the request: [" + ticket.getId() + "/" + approver + "] was allready procesed. Waiting for response.");
                            ticket.setAllowClean(true);
                        }
                    }else{
                        logger.error("INFO: Invalid User - Ticket#" + ticket.getId() + " - Approver:'" + approver + "'");
                        ticket.addValidationErrors("Approver Validation Error: '" + approver + "' doesn't exist");
                    }
                }
                
                List<String> approvalRequest = ticket.getApprovalRequest();
                List<String> validationError = ticket.getValidationErrors();
                // si luego de las validaciones 'approvalRequest' esta vacío. No envio nada y sigo con el otro ticket.
                if(!approvalRequest.isEmpty() || !validationError.isEmpty() || ticket.isAllowClean()){
                    //envia mail a kace para actualizar el ticket
                    if(sendUpdateToKace(ticket, smtpSession)){
                        // si el envio del mail es true, envio los request a gata y guardo en local DB
                        for(String approver: approvalRequest){
                            // post to gata
                            logger.error("INFO: Sending request to GATA [" + ticket.getId() + "/" + approver + "]");
                            if(postToGATA(ticket, approver)){
                                logger.error("INFO: GATA received the request: [" + ticket.getId() + "/" + approver + "]");
                                // guarda en local DB el request
                                approvalService.save(ticket.getId(), approver);
                                logger.error("INFO: request saved in local DB: [" + ticket.getId() + "/" + approver + "]");
                            }else{
                                logger.error("ERROR: while sending data to GATA - Ticket#" + ticket.getId() + " - Arrover:'" + approver + "'");
                            }
                        }
                    }else{
                        logger.error("ERROR: while sending email to KACE - Ticket#" + ticket.getId());
                    }
                }
            }
            ctx.close();
            return true;
        }catch(LdapContextException e){
            logger.error("ERROR: LDAP Service - Can't get 'DirContext'");
            return false;
        }catch(NamingException e){
            logger.error("INFO: Couldn't close Ldap Context");
            return true;
        }catch(Exception e){
            logger.error("ERROR: Unexpected error - Update process ended");
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
            java.util.logging.Logger.getLogger(ProcessServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "2099-12-01";
        }
    }
    
    private boolean sendUpdateToKace(Ticket ticket, Session smtpSession){
        
        String subject = "TICK:" + ticket.getId();
        
        String content = 
                "@cc_list=" + getActualCcList(ticket) + nl +
                "@custom_" + config.getApproversField() + "=" + nl;
        
        if(!ticket.getApprovalRequest().isEmpty()){
            content = content +
                "## KGI Messege: ..." + nl +
                "## The approval request was sent to GATA for the user/s: ";
                for(String approver: ticket. getApprovalRequest())content = content + "[" + approver + "] ";
            content = content + nl +
                "## waiting for response... ";
        }
        
        if(!ticket.getValidationErrors().isEmpty()){
            content = content + nl + nl + "## Errors Foud: ...";
            for(String error : ticket.getValidationErrors()){
                content = content + nl + 
                        "## [" + error + "]";
            }
        }
                
        if(inbox.Send(subject, content, smtpSession)){
            return true;
        }
        return false;
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
        }catch(KaceMailingException | NoApprovalRequestFoudException e){
            map.put("status", "Error");
            map.put("Description", "Ticket[" + ticketNum + "] Update Error: " + e.getMessage());
        }
        return map;
    }
    
    
    private void sendApprovalToKace(String isApproved, String ticketNum, String approver, String comment) throws KaceMailingException, NoApprovalRequestFoudException{
        
        if(approvalService.exist(Integer.parseInt(ticketNum), approver)){
            
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
            if(!inbox.Send(subject, content, inbox.getSession()))throw new KaceMailingException("Mail to Kace Failure");
            
        }else{
            throw new NoApprovalRequestFoudException("Approval Request not found for the user: '" + approver + "'");
        }
        
        
    }
    
    
    

    
}
