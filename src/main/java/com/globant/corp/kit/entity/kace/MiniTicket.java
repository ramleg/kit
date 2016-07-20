package com.globant.corp.kit.entity.kace;

import java.util.Date;
import javax.persistence.*;
/**
 *
 * @author ramiro.acoglanis
 */
@Entity(name="KaceTicket")
@Table(name="HD_TICKET")
public class MiniTicket {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
    @Column(name="CUSTOM_FIELD_VALUE1")
    private String approvers;
    
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

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getApprovers() {
        return approvers;
    }

    public void setApprovers(String approvers) {
        this.approvers = approvers;
    }
    
    
}
