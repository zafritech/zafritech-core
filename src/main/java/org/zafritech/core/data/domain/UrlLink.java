package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "CORE_URL_LINKS")
public class UrlLink implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;

    private String Identifier;

    private String linkTitle;
    
    private String linkUrl;
    
    private String sourceAuthority;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public UrlLink() {
        
    }

    public UrlLink(String linkTitle, String linkUrl, String linkSourceAuthority) {
        
        this.uuId = UUID.randomUUID().toString();
        this.linkTitle = linkTitle;
        this.linkUrl = linkUrl;
        this.sourceAuthority = linkSourceAuthority;
        this.creationDate = new Timestamp(System.currentTimeMillis());
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

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String Identifier) {
        this.Identifier = Identifier;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getSourceAuthority() {
        return sourceAuthority;
    }

    public void setSourceAuthority(String sourceAuthority) {
        this.sourceAuthority = sourceAuthority;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

