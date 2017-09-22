package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.core.enums.ClaimRelations;

@Entity(name = "CORE_CLAIM_TYPES")
public class ClaimType implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    private String typeName;
    
    private String entityType;
    
    @Enumerated(EnumType.STRING)
    private ClaimRelations typeRelation;
    
    private String typeDescription;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public ClaimType() {
        
    }

    public ClaimType(String typeName, String entityType, ClaimRelations typeRelation) {
        
        this.uuId = UUID.randomUUID().toString();
        this.typeName = typeName;
        this.entityType = entityType;
        this.typeRelation = typeRelation;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public ClaimType(String typeName, String entityType, ClaimRelations typeRelation, String typeDescription) {
        
        this.uuId = UUID.randomUUID().toString();
        this.typeName = typeName;
        this.entityType = entityType;
        this.typeRelation = typeRelation;
        this.typeDescription = typeDescription;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "ClaimType{" + "id=" + id + ", uuId=" + uuId + ", typeName=" + 
                typeName + ", entityType=" + entityType + ", typeRelation=" + 
                typeRelation + ", typeDescription=" + typeDescription + 
                ", creationDate=" + creationDate + '}';
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public ClaimRelations getTypeRelation() {
        return typeRelation;
    }

    public void setTypeRelation(ClaimRelations typeRelation) {
        this.typeRelation = typeRelation;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

