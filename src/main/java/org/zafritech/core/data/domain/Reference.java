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
import org.zafritech.core.enums.ReferenceSources;

@Entity(name = "CORE_REFERENCES")
public class Reference implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    @Enumerated(EnumType.STRING)
    private ReferenceSources sourceType;
    
    private Long idValue;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public Reference() {
        
    }

    public Reference(ReferenceSources sourceType, Long idValue) {
        
        this.uuId = UUID.randomUUID().toString();
        this.sourceType = sourceType;
        this.idValue = idValue;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "Reference{" + "id=" + getId() + ", uuId=" + getUuId() + ", sourceType=" 
                + getSourceType() + ", idValue=" + getIdValue() + ", creationDate=" 
                + getCreationDate() + '}';
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

    public ReferenceSources getSourceType() {
        return sourceType;
    }

    public void setSourceType(ReferenceSources sourceType) {
        this.sourceType = sourceType;
    }

    public Long getIdValue() {
        return idValue;
    }

    public void setIdValue(Long idValue) {
        this.idValue = idValue;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

