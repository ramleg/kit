package com.globant.corp.kit.entity.kace;

import com.globant.corp.kit.entity.local.ApprovalRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author ramiro.acoglanis
 */

@Entity(name="Ticket")
@Table(name="HD_TICKET")
public class Ticket {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String summary;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SUBMITTER_ID")
    private KaceUser submitter;
    @Column(name="CC_LIST")
    private String ccList;
    @Column(name="DUE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date duedate;
    @Column(name="HD_QUEUE_ID")
    private Integer queueId;
    @Column(name="CUSTOM_FIELD_VALUE0")
    private String approvers;
    @Column(name="HD_STATUS_ID")
    private Integer status;
    @Column(name="CUSTOM_FIELD_VALUE1")
    private String project;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "HD_CATEGORY_ID")
    private Category category;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "HD_PRIORITY_ID")
    private Priority priority;
    
    @Transient
    private boolean allowClean = false;
    @Transient
    private List<ApprovalRequest> approvalRequest = new ArrayList<>();
    @Transient
    private List<String> validationErrors = new ArrayList<>();
    @Transient
    private String newOrModified;
    @Transient
    private List<TicketDetails> details = new ArrayList<>();
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public KaceUser getSubmitter() {
        return submitter;
    }

    public void setSubmitter(KaceUser submitter) {
        this.submitter = submitter;
    }

    public String getCcList() {
        return ccList;
    }

    public void setCcList(String ccList) {
        this.ccList = ccList;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public Integer getQueueId() {
        return queueId;
    }

    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getApprovers() {
        return approvers;
    }

    public List<String> getApproversList() {
        
        String[] arr = getApprovers().split(",");
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < arr.length; i++){
            list.add(arr[i].trim());
        }
        return list;
    }
    
    public String getApproversForCcList(){
        String result="";
        for(String approver: getApproversList()){
            result = result + ", " + approver + "@globant.com";
        }
        return result;
    }
    
    public void setApprovers(String approvers) {
        this.approvers = approvers;
    }

    public boolean isAllowClean() {
        return allowClean;
    }

    public void setAllowClean(boolean allowClean) {
        this.allowClean = allowClean;
    }

    public List<ApprovalRequest> getApprovalRequest() {
        return approvalRequest;
    }

    public void setApprovalRequest(List<ApprovalRequest> approvalRequest) {
        this.approvalRequest = approvalRequest;
    }

    public void addApprovalRequest(ApprovalRequest request) {
        this.approvalRequest.add(request);
    }
    
    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }
    
    public void addValidationErrors(String error) {
        this.validationErrors.add(error);
    }
    
    public String getNewOrModified() {
        return newOrModified;
    }

    public void setNewOrModified(String newOrModified) {
        this.newOrModified = newOrModified;
    }

    public List<TicketDetails> getDetails() {
        return details;
    }

    public void setDetails(List<TicketDetails> details) {
        this.details = details;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    
}
