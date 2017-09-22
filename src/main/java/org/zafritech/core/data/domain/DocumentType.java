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

@Entity(name = "CORE_DOCUMENT_TYPES")
public class DocumentType implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;

    @Column(nullable = false)
    private String typeCode;

    @Column(nullable = false)
    private String documentTypeName;
    
    @ManyToOne
    @JoinColumn(name = "descriptorId")
    private DocumentContentDescriptor contentDescriptor; 

    @Column(columnDefinition = "TEXT")
    private String documentTypeDescription;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public DocumentType() {
        
    }

    public DocumentType(String typeCode,
                        String documentTypeName,
                        DocumentContentDescriptor contentDescriptor) {
        
        this.uuId = UUID.randomUUID().toString();
        this.typeCode = typeCode;
        this.documentTypeName = documentTypeName;
        this.contentDescriptor = contentDescriptor;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public DocumentType(String typeCode,
                        String documentTypeName, 
                        DocumentContentDescriptor contentDescriptor, 
                        String documentTypeDescription) {
        
        this.uuId = UUID.randomUUID().toString();
        this.typeCode = typeCode;
        this.documentTypeName = documentTypeName;
        this.contentDescriptor = contentDescriptor;
        this.documentTypeDescription = documentTypeDescription;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "DocumentType{" + "id=" + getId() + ", uuId=" + 
                getUuId() + ", typeCode=" + getTypeCode() + ", documentTypeName=" + 
                getDocumentTypeName() + ", contentDescriptor=" + 
                getContentDescriptor() + ", documentTypeLongName=" + 
                getDocumentTypeDescription() + ", creationDate=" + 
                getCreationDate() + ", modifiedDate=" + getModifiedDate() + '}';
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

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    public DocumentContentDescriptor getContentDescriptor() {
        return contentDescriptor;
    }

    public void setContentDescriptor(DocumentContentDescriptor contentDescriptor) {
        this.contentDescriptor = contentDescriptor;
    }

    public String getDocumentTypeDescription() {
        return documentTypeDescription;
    }

    public void setDocumentTypeDescription(String documentTypeDescription) {
        this.documentTypeDescription = documentTypeDescription;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}

