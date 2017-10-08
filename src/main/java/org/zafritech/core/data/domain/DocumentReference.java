package org.zafritech.core.data.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.core.enums.ReferenceTypes;

@Entity(name = "CORE_DOCUMENT_REFERENCES")
public class DocumentReference implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    private String docRefId;
    
    @ManyToOne
    @JoinColumn(name = "referenceId")
    @JsonBackReference
    private Reference reference;
    
    @ManyToOne
    @JoinColumn(name = "documentId")
    @JsonBackReference
    private Document document;
    
    @Enumerated(EnumType.STRING)
    private ReferenceTypes referenceType;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public DocumentReference() {
        
    }

    public DocumentReference(Reference reference, 
                             Document document, 
                             ReferenceTypes referenceType) {
        
        this.uuId = UUID.randomUUID().toString();
        this.reference = reference;
        this.document = document;
        this.referenceType = referenceType;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public DocumentReference(String docRefId, 
                             Reference reference, 
                             Document document, 
                             ReferenceTypes referenceType) {
        
        this.uuId = UUID.randomUUID().toString();
        this.docRefId = docRefId;
        this.reference = reference;
        this.document = document;
        this.referenceType = referenceType;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "Document References {" + "id=" + getId() + ", uuId=" + 
                getUuId() + ", reference=" + getReference() + ", referenceType=" + 
                getReferenceType() + ", creationDate=" + getCreationDate() + '}';
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

    public String getDocRefId() {
        return docRefId;
    }

    public void setDocRefId(String docRefId) {
        this.docRefId = docRefId;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public ReferenceTypes getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceTypes referenceType) {
        this.referenceType = referenceType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

