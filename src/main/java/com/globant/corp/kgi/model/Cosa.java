package com.globant.corp.kgi.model;

import javax.persistence.*;
import org.springframework.stereotype.Component;
/**
 *
 * @author ramiro.acoglanis
 */

@Entity(name="cosas")
public class Cosa {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private String foo;

    public Cosa() {}
     
    public Cosa(String description, String foo) {
        this.description = description;
        this.foo = foo;
    }
     
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }
}
