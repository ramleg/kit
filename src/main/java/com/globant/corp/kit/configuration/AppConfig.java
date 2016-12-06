package com.globant.corp.kit.configuration;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author ramiro.acoglanis
 */

@Component
public class AppConfig {
    
    @Value("${kit.application.allow-rebuild}")
    private String allowRebuild;
    @Value("${kit.application.pending-approval-state}")
    private String pendingApprovalState;
    @Value("${kit.application.approved-state}")
    private String approvedState;
    @Value("${kit.application.rejected-state}")
    private String rejectedState;
    @Value("${kit.application.approvers-custom-field}")
    private String approversField;
    @Value("${kit.application.allowed-queues}")
    private String allowedQueues;
    @Value("${kit.approval-url}")
    private String approvalURL;
    @Value("${kit.reject-url}")
    private String rejectURL;
    
    @Value("${kit.ldap.server}")
    private String ldapServer;
    @Value("${kit.ldap.alt-server}")
    private String altLdapServer;
    @Value("${kit.ldap.admin-user}")
    private String ldapAdminUser;
    @Value("${kit.ldap.admin-passwd}")
    private String ldapAdminPasswd;
    
    public boolean isAllowRebuild() {
        if (allowRebuild.equals("true")){
            return true;
        }else{
            return false;
        }
            
    }

    public String getPendingApprovalState() {
        return pendingApprovalState;
    }

    public String getApprovedState(){
        return approvedState;
    }

    public String getRejectedState() {
        return rejectedState;
    }

    public String getApproversField() {
        return approversField;
    }    
    
    public String getAllowedQueues() {
        return allowedQueues;
    }
    
    public List<Integer> getAllowedQueuesList() {
        List<Integer> queueList = new ArrayList<>();
        for(String queue : this.allowedQueues.split(",")){
            queueList.add(Integer.parseInt(queue.trim()));
        }
        return queueList;
    }

    public String getApprovalURL() {
        return approvalURL;
    }

    public String getRejectURL() {
        return rejectURL;
    }
    
    public String getAllowRebuild() {
        return allowRebuild;
    }

    public String getLdapServer() {
        return ldapServer;
    }

    public String getAltLdapServer() {
        return altLdapServer;
    }

    public String getLdapAdminUser() {
        return ldapAdminUser;
    }

    public String getLdapAdminPasswd() {
        return ldapAdminPasswd;
    }
    
    
    
}
