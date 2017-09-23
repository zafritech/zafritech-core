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
    
    private String refNumber;
    
    private String refTitle;
    
    private String refVersion;
    
    private String refAuthority;
    
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

    public Reference(ReferenceSources sourceType, 
                     Long idValue, 
                     String refNumber, 
                     String refTitle, 
                     String refVersion,
                     String refAuthority) {
        
        this.uuId = UUID.randomUUID().toString();
        this.sourceType = sourceType;
        this.idValue = idValue;
        this.refNumber = refNumber;
        this.refTitle = refTitle;
        this.refVersion = refVersion;
        this.refAuthority = refAuthority;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "Reference{" + "id=" + id + ", uuId=" + uuId + ", sourceType=" 
                + sourceType + ", idValue=" + idValue + ", refNumber=" 
                + refNumber + ", refTitle=" + refTitle + ", refVersion=" 
                + refVersion + ", refAuthority=" + refAuthority + ", creationDate=" 
                + creationDate + '}';
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

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public String getRefTitle() {
        return refTitle;
    }

    public void setRefTitle(String refTitle) {
        this.refTitle = refTitle;
    }

    public String getRefVersion() {
        return refVersion;
    }

    public void setRefVersion(String refVersion) {
        this.refVersion = refVersion;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getRefAuthority() {
        return refAuthority;
    }

    public void setRefAuthority(String refAuthority) {
        this.refAuthority = refAuthority;
    }
}

