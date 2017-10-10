package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "CORE_ENTITY_TYPES")
public class EntityType implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    private String entityTypeKey;
    
    private String entityTypeName;
    
    private String entityTypeCode;
    
    @Column(columnDefinition = "TEXT")
    private String entityTypeDescription;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public EntityType() {
        
    }

    public EntityType(String entityTypeKey, String entityTypeName, String entityTypeCode, String entityTypeDescription) {
        
        this.uuId = UUID.randomUUID().toString();
        this.entityTypeKey = entityTypeKey;
        this.entityTypeName = entityTypeName;
        this.entityTypeCode = entityTypeCode;
        this.entityTypeDescription = entityTypeDescription;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "EntityType{" + "id=" + getId() + ", uuId=" + getUuId() 
                + ", entityTypeKey=" + getEntityTypeKey() + ", entityTypeName=" 
                + getEntityTypeName() + ", entityTypeCode=" + getEntityTypeCode() 
                + ", entityTypeDescription=" + getEntityTypeDescription() 
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

    public String getEntityTypeKey() {
        return entityTypeKey;
    }

    public void setEntityTypeKey(String entityTypeKey) {
        this.entityTypeKey = entityTypeKey;
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }

    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public String getEntityTypeCode() {
        return entityTypeCode;
    }

    public void setEntityTypeCode(String entityTypeCode) {
        this.entityTypeCode = entityTypeCode;
    }

    public String getEntityTypeDescription() {
        return entityTypeDescription;
    }

    public void setEntityTypeDescription(String entityTypeDescription) {
        this.entityTypeDescription = entityTypeDescription;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

