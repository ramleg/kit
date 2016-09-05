package com.globant.corp.kit.entity.local;

/**
 *
 * @author ramiro.acoglanis
 */


public class PostBody {
    
    private String title;
    private String approver;

    public PostBody(String title, String approver) {
        this.title = title;
        this.approver = approver;
    }

    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }
    
    
    
}
