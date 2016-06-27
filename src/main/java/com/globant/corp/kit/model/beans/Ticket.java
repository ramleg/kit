package com.globant.corp.kit.model.beans;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ramiro.acoglanis
 */
@Entity(name="Ticket")
@Table(name="ticket")
public class Ticket {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id = 0;
    private Integer num;
    private String title;
    private String summary;
    private String queue;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modif_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifDate;
    private String submitter;
    private String owner;
    private String status;
    private String location;
    private String category1;
    private String category2;
    private String category3;
    private String priority;
    private String project;
    private String url;
    //---------------------------------
    @OneToMany(mappedBy = "ticket")
    private List<Comment> comments;

    @OneToMany(mappedBy = "ticket")
    private List<Approver> approvers;
    
}
