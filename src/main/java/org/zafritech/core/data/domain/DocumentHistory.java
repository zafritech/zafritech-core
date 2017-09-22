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

@Entity(name = "CORE_DOCUMENT_HISTORIES")
public class DocumentHistory implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    @ManyToOne
    @JoinColumn(name = "documentId")
    private Document document;
    
    private String ecpNumber;
    
    @Column(columnDefinition = "TEXT")
    private String changeSummary;
    
    private String revision;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeDate;

    public DocumentHistory() {
        
    }

    public DocumentHistory(Document document, String changeSummary, String changeRevision) {
        
        this.uuId = UUID.randomUUID().toString();
        this.document = document;
        this.changeSummary = changeSummary;
        this.revision = changeRevision;
        this.changeDate = new Timestamp(System.currentTimeMillis());
    }

    public DocumentHistory(Document document, String ecpNumber, String changeSummary, String changeRevision) {
        
        this.uuId = UUID.randomUUID().toString();
        this.document = document;
        this.ecpNumber = ecpNumber;
        this.changeSummary = changeSummary;
        this.revision = changeRevision;
        this.changeDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "DocumentHistory{" + "id=" + getId() + ", uuId=" + getUuId() + ", document=" 
                + getDocument() + ", ecpNumber=" + getEcpNumber() + ", changeSummary=" 
                + getChangeSummary() + ", changeRevision=" + getRevision() + ", changeDate=" 
                + getChangeDate() + '}';
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getEcpNumber() {
        return ecpNumber;
    }

    public void setEcpNumber(String ecpNumber) {
        this.ecpNumber = ecpNumber;
    }

    public String getChangeSummary() {
        return changeSummary;
    }

    public void setChangeSummary(String changeSummary) {
        this.changeSummary = changeSummary;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }
}

