package com.globant.corp.kit.entity.kace;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ramiro.acoglanis
 */
@Entity(name="FullTicket")
@Table(name="HD_TICKET")
public class FullTicket {
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String summary;
    @Column(name="CUSTOM_FIELD_VALUE1")
    private String approvers;
    
    
    @OneToMany(mappedBy = "hdTicketId", fetch = FetchType.EAGER)
    private List<FullHistory> history;
    
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
    
    public void setApprovers(String approvers) {
        this.approvers = approvers;
    }

    public List<FullHistory> getHistory() {
        return history;
    }

    public void setHistory(List<FullHistory> history) {
        this.history = history;
    }

    @Override
    public String toString(){
        return null;
    }
    

    
    
}
