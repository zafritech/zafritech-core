package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "CORE_BASELINES")
public class BaseLine implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    @ManyToOne
    @JoinColumn(name = "typeId")
    private EntityType type;
    
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public BaseLine() {
        
        this.uuId = UUID.randomUUID().toString();
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public BaseLine(EntityType type, String name, String description) {
        
        this.uuId = UUID.randomUUID().toString();
        this.type = type;
        this.name = name;
        this.description = description;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "BaseLine{" + "id=" + getId() + ", uuId=" + getUuId() 
                + ", type=" + getType() + ", name=" + getName() 
                + ", description=" + getDescription() 
                + ", creationDate=" + getCreationDate() + '}';
    }

    public Long getId() {
        return id;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

