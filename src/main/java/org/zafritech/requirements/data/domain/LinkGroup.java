package org.zafritech.requirements.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.core.data.domain.Document;

@Entity(name = "REQUIREMENTS_LINK_GROUPS")
public class LinkGroup implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    @ManyToOne
    @JoinColumn(name = "srcDocumentId")
    private Document sourceDocument;
    
    @ManyToOne
    @JoinColumn(name = "dstDocumentId")
    private Document destinationDocument;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public LinkGroup() {
        
    }

    public LinkGroup(Document sourceDocument, Document destinationDocument) {
        
        this.uuId = UUID.randomUUID().toString();
        this.sourceDocument = sourceDocument;
        this.destinationDocument = destinationDocument;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "LinkGroup{" + "id=" + getId() + ", uuId=" + 
                getUuId() + ", sourceDocument=" + getSourceDocument() + ", destinationDocument=" + 
                getDestinationDocument() + ", creationDate=" + getCreationDate() + '}';
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

    public Document getSourceDocument() {
        return sourceDocument;
    }

    public void setSourceDocument(Document sourceDocument) {
        this.sourceDocument = sourceDocument;
    }

    public Document getDestinationDocument() {
        return destinationDocument;
    }

    public void setDestinationDocument(Document destinationDocument) {
        this.destinationDocument = destinationDocument;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

