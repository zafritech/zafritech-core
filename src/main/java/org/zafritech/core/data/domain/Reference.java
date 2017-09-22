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
import org.zafritech.core.enums.MediaFormat;

@Entity(name = "CORE_REFERENCES")
public class Reference implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    private String refNumber;
    
    private String refTitle;
    
    @Enumerated(EnumType.STRING)
    private MediaFormat refFormat;
    
    private String refVersion;
    
    private String refLink;
    
    private String authority;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public Reference() {
        
    }

    public Reference(String refNumber, 
                     String refTitle, 
                     MediaFormat refFormat, 
                     String refVersion, 
                     String refLink) {
        
        this.uuId = UUID.randomUUID().toString();
        this.refNumber = refNumber;
        this.refTitle = refTitle;
        this.refFormat = refFormat;
        this.refVersion = refVersion;
        this.refLink = refLink;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    

    public Reference(String refNumber, 
                     String refTitle, 
                     MediaFormat refFormat, 
                     String refVersion, 
                     String refLink, 
                     String authority) {
        
        this.uuId = UUID.randomUUID().toString();
        this.refNumber = refNumber;
        this.refTitle = refTitle;
        this.refFormat = refFormat;
        this.refVersion = refVersion;
        this.refLink = refLink;
        this.authority = authority;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "Reference{" + "id=" + getId() + ", uuId=" + getUuId() + ", refFormat=" + 
                getRefFormat() + ", refTitle=" + getRefTitle() + ", refVersion=" + 
                getRefVersion() + ", refLink=" + getRefLink() + ", authority=" + 
                getAuthority() + ", creationDate=" + getCreationDate() + '}';
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

    public MediaFormat getRefFormat() {
        return refFormat;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public void setRefFormat(MediaFormat refFormat) {
        this.refFormat = refFormat;
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

    public String getRefLink() {
        return refLink;
    }

    public void setRefLink(String refLink) {
        this.refLink = refLink;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

