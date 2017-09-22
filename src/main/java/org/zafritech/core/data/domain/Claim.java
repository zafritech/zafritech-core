package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Table(uniqueConstraints = @UniqueConstraint(columnNames={"claimTypeId", "claimValue"}))
@Entity(name = "CORE_CLAIMS")
public class Claim implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    @ManyToOne
    @JoinColumn(name = "claimTypeId")
    private ClaimType claimType;
    
    private Long claimValue;
    
    private String claimDescription;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public Claim() {
        
    }

    public Claim(ClaimType claimType, Long claimValue) {
        
        this.uuId = UUID.randomUUID().toString();
        this.claimType = claimType;
        this.claimValue = claimValue;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public Claim(ClaimType claimType, Long claimValue, String claimDescription) {
        
        this.uuId = UUID.randomUUID().toString();
        this.claimType = claimType;
        this.claimValue = claimValue;
        this.claimDescription = claimDescription;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "Claim{" + "id=" + id + ", uuId=" + 
                uuId + ", claimType=" + claimType + ", claimValue=" + 
                claimValue + ", claimDescription=" + 
                claimDescription + ", creationDate=" + creationDate + '}';
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

    public ClaimType getClaimType() {
        return claimType;
    }

    public void setClaimType(ClaimType claimType) {
        this.claimType = claimType;
    }

    public Long getClaimValue() {
        return claimValue;
    }

    public void setClaimValue(Long claimValue) {
        this.claimValue = claimValue;
    }

    public String getClaimDescription() {
        return claimDescription;
    }

    public void setClaimDescription(String claimDescription) {
        this.claimDescription = claimDescription;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

