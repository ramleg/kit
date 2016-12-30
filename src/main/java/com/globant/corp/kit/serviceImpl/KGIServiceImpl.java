package com.globant.corp.kit.serviceImpl;

import com.globant.corp.kit.configuration.AppConfig;
import com.globant.corp.kit.entity.kace.Queue;
import com.globant.corp.kit.entity.kace.Ticket;
import com.globant.corp.kit.entity.local.ApprovalRequest;
import com.globant.corp.kit.exception.KaceMailingException;
import com.globant.corp.kit.exception.LdapContextException;
import com.globant.corp.kit.exception.NoApprovalRequestFoudException;
import com.globant.corp.kit.service.ApprovalRequestService;
import com.globant.corp.kit.service.InboxService;
import com.globant.corp.kit.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.globant.corp.kit.service.QueueService;
import org.springframework.beans.factory.annotation.Value;
import com.globant.corp.kit.service.RestServiceConsumer;
import com.globant.corp.kit.service.TicketService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.mail.Session;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import com.globant.corp.kit.service.KGIService;
import java.util.logging.Level;

/**
 *
 * @author ramiro.acoglanis
 */
@Service
public class KGIServiceImpl implements KGIService{

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
    public boolean update(){
        
        Session smtpSession = inbox.getSession();
        List<Queue> queueList = queueService.getAllwedQueues();
        List<Integer> queuesIds = queueService.getQueuesIds(queueList);
        List<Ticket> pendingApprovalTickets = ticketService.getPendingApprovalTicketsList(queuesIds);
        // Comienza el proceso siempre que haya tickets con approvals pendientes
        if(!pendingApprovalTickets.isEmpty()){
            try{
            DirContext ctx = ldap.getContext();
            logger.info("*** Update Proccess Started!!");
            // Itero la lista de ticket extraidos de la DB de Kace
            for(Ticket ticket : pendingApprovalTickets){
                // Itero cada ticket segun la cantidad de approvers que tiene
                for(String approver : ticket.getApproversList()){
                    // Valido individualmente cada approver antes de ejecutar el proceso
                    if(ldap.validateUser(approver, ctx)){
                        // si la combinacion de num de ticket y approver existe en la DB local, 
                        // significa que que el request ya se proceso y esta a la espera de aprobacion
                        ApprovalRequest request = approvalService.getByTicketNumAndApprover(ticket.getId(), approver);
                        if(request == null){
                            // las validaciones son positivas por lo que agrego el request a la lista
                            ticket.addApprovalRequest(new ApprovalRequest(ticket.getId(), approver));
                        }else{
                            logger.info("The request: [TICK#" + ticket.getId() + "|APPROVER:" + approver + "] was allready procesed. Waiting for response.");
                            ticket.setAllowClean(true);
                        }
                    }else{
                        logger.info("Invalid User - [TICK:" + ticket.getId() + " | APPROVER:'" + approver + "']");
                        ticket.addValidationErrors("Approver Validation Error: '" + approver + "' doesn't exist");
                    }
                }
                
                List<ApprovalRequest> approvalRequest = ticket.getApprovalRequest();
                List<String> validationError = ticket.getValidationErrors();
                // si luego de las validaciones tengo Request, Errores o hay que limpiar el ticket, envio el mail correspondiente a Kace
                if(!approvalRequest.isEmpty() || !validationError.isEmpty() || ticket.isAllowClean()){
                    //envia mail a kace para actualizar el ticket
                    String emailTo = queueService.getEmail(queueList, ticket.getQueueId());
                    if(sendUpdateToKace(ticket, emailTo, smtpSession)){
                        logger.info("Kace Updated via email - [TICK:" + ticket.getId() + "]");
                        // si el envio del mail es true, envio los request a gata y guardo en local DB
                        for(ApprovalRequest request: approvalRequest){
                            // post to gata
                            if(postToGATA(ticket, request)){
                                logger.info("GATA received the request: [TICK:" + request.getTicketNum()+ " | APPROVER:'" + request.getApprover() + "']");
                                // guarda en local DB el request
                                approvalService.save(request.getTicketNum(), request.getApprover());
                                logger.info("Request saved in local DB: [TICK:" + request.getTicketNum()+ " | APPROVER:'" + request.getApprover() + "']");
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
                java.util.logging.Logger.getLogger(KGIServiceImpl.class.getName()).log(Level.SEVERE, null, e);
                return false;
            }
        }else{
            return false;
        }
    }
    
    
    private boolean postToGATA(Ticket ticket, ApprovalRequest request){
        
        HashMap<String, String> body = new HashMap<>();
            body.put("title", "Approval Request for Ticket#" + ticket.getId());
            body.put("dueDate",dueDate(30));
            body.put("approvalRequestTypeId", "34");
            body.put("approver", request.getApprover());
            body.put("body", "<html><body>" + gataHtmlBody(ticket) + "</body></html>");
            body.put("author", ticket.getSubmitter().getUserName());
            body.put("description", ticket.getSummary());
            body.put("language", "english");
            body.put("approveUrl", config.getApprovalURL() + request.getId() + "/");
            body.put("rejectUrl", config.getRejectURL() + request.getId() + "/");

            ResponseEntity response = restConsumer.postToGata(body);
            if(response.getStatusCodeValue()==201){
                return true;
            }else{
                logger.error("while sending data to GATA - [ID#" + request.getId() + " TICK:" + request.getTicketNum()+ " | APPROVER:'" + request.getApprover() + "']");
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
                for(ApprovalRequest request: ticket. getApprovalRequest())content = content + "[" + request.getApprover() + "] ";
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
        for(ApprovalRequest request : ticket.getApprovalRequest()){
            if(!cclist.contains(request.getApprover())){
                if(cclist.equals("")){
                    cclist = cclist + request.getApprover() + "@globant.com";
                }else{
                    cclist = cclist + ", " + request.getApprover() + "@globant.com";
                }
            }
        }
        return cclist;
    }
    
    @Override
    public HashMap<String,String> kgiEndPoint(String isApproved, String requestID, String comment, String token) {
        
        // send email to kace to update the ticket
        HashMap<String,String> map = new HashMap<>();
        ApprovalRequest request = new ApprovalRequest();
        try{
            request = approvalService.getById(Integer.parseInt(requestID));
            sendApprovalToKace(isApproved.toUpperCase(), request.getTicketNum().toString(), request.getApprover(), comment);
            approvalService.delete(request.getId());
            map.put("status", isApproved);
            map.put("Description", "The ticket#" + request.getTicketNum() + " was processed");            
        }catch(KaceMailingException e){
            map.put("status", "Error");
            map.put("Description", e.getMessage() + " [TICK:" + request.getTicketNum() + " | APPROVER:'" + request.getApprover() + "']");
            logger.error("<-- Wile sending approval to Kace via mail [TICK:" + request.getTicketNum() + " | APPROVER:'" + request.getApprover() + "']");
        } catch (NoApprovalRequestFoudException e){
            map.put("status", "Error");
            map.put("Description", e.getMessage() + " [TICK:" + request.getTicketNum() + " | APPROVER:'" + request.getApprover() + "']");
            logger.error("<-- Request not found in local DB [TICK:" + request.getTicketNum() + " | APPROVER:'" + request.getApprover() + "']");
        }
        return map;
    }
    
    private void sendApprovalToKace(String approvalStatus, String ticketNum, String approver, String comment) throws KaceMailingException{
        
            String queueEmail = queueService.getEmail(Integer.parseInt(ticketNum));
            
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
            logger.info("<-- The User: '" + approver + "' - " + approvalStatus + " the request for the TICK:" + ticketNum);
    }
    
    
    private String gataHtmlBody(Ticket ticket){
        
        return "<div style=\"max-width: 100%;max-height:100%;padding: 7px 7px;\">\n" +
                "    <div style=\"line-height: 30px; min-height: 190px;padding: 14px; font-family: lucida, sans-serif;\">\n" +
                "        <div style=\"text-align: right;font-size: 14px;color: #979797;\">\n" +
                "            TKT\n" +
                "            <span style=\"font-family: lucida, sans-serif;color: #464646;\">\n" +
                ticket.getId() +
                "            </span>\n" +
                "        </div>\n" +
                "        <div style=\"font-family: lucida, sans-serif;text-align: left;width: 100%;margin: 7px 0 5px 0;font-size: 14px;color: #979797;\">\n" +
                "            TITLE\n" +
                "        </div>\n" +
                "        <div style=\"width: 100%;font-size: 14px;color: #464646;margin-bottom: 5px;\">\n" +
                ticket.getTitle() +
                "        </div>\n" +
                "        <hr style=\"border: none;border-top: 2px dashed #f1f1f1;width: 100%;margin: 0;padding-top: 0px;\">\n" +
                "            <div style=\"font-family: lucida,sans-serif;width: 100%;margin: 7px 0px 7px 0px;color: #464646;font-size: 14px;color: #979797;\">\n" +
                "                CATEGORY\n" +
                "            </div>\n" +
                "            <div style=\"color: #464646;font-size: 14px;\">\n" +
                ticket.getCategory() +
                "            </div>\n" +
                "        \n" +
                "        <hr style=\"border: none;border-top: 2px dashed #f1f1f1;width: 100%;margin: 0;padding-top: 0px;\">\n" +
                "        	<div style=\"font-family: lucida,sans-serif;width: 100%;margin: 7px 0px 7px 0px;color: #464646;font-size: 14px;color: #979797;\">\n" +
                "                PRIORITY\n" +
                "            </div>\n" +
                "            <div style=\"color: #fff;font-size: 14px;font-weight:bold;\">\n" +
                ticket.getPriority() +
                "            </div>\n" +
                "        \n" +
                "        <hr style=\"border: none;border-top: 2px dashed #f1f1f1;width: 100%;margin: 0;padding-top: 0px;\">\n" +
                "			<div style=\"font-family: lucida,sans-serif;width: 100%;margin: 7px 0px 7px 0px;color: #464646;font-size: 14px;color: #979797;\">\n" +
                "                PROJECT\n" +
                "            </div>\n" +
                "            <div style=\"width: 100%;font-size: 14px;color: #464646;margin-bottom: 5px;\">\n" +
                ticket.getProject() +
                "        	</div>\n" +
                "        <hr style=\"border: none;border-top: 2px dashed #f1f1f1;width: 100%;margin: 0;padding-top: 0px;\">\n" +
                "			<div style=\"font-family: lucida,sans-serif;width: 100%;margin: 7px 0px 7px 0px;color: #464646;font-size: 14px;color: #979797;\">\n" +
                "                SUMMARY\n" +
                "            </div>\n" +
                "            <div style=\"width: 100%;font-size: 14px;color: #464646;margin-bottom: 5px;\">\n" +
                ticket.getSummary() +
                "        	</div>\n" +
                "    </div>\n" +
                "</div>";
        
    }
    
}
