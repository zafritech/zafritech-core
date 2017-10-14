package org.zafritech.requirements.data.domain;

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
import org.zafritech.core.data.domain.DocumentContentDescriptor;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.User;

@Entity(name = "REQUIREMENTS_TEMPLATES")
public class Template implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    private String templateName;
    
    private String templateLongName;

    @Column(columnDefinition = "TEXT")
    private String templateDescription;
    
    @ManyToOne
    @JoinColumn(name = "descriptorId")
    private DocumentContentDescriptor contentDescriptor;
    
    @ManyToOne
    @JoinColumn(name = "documentTypeId")
    private EntityType documentType;
    
    @ManyToOne
    @JoinColumn(name = "ownerId")
    private User owner;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public Template() {
        
    }

    public Template(String templateName, 
                    DocumentContentDescriptor contentDescriptor, 
                    EntityType documentType,
                    User owner) {
        
        this.uuId = UUID.randomUUID().toString();
        this.templateName = templateName;
        this.contentDescriptor = contentDescriptor;
        this.documentType = documentType;
        this.owner = owner;
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public Template(String templateName, 
                    String templateLongName, 
                    String templateDescription, 
                    DocumentContentDescriptor contentDescriptor, 
                    EntityType documentType,
                    User owner) {
        
        this.uuId = UUID.randomUUID().toString();
        this.templateName = templateName;
        this.templateLongName = templateLongName;
        this.templateDescription = templateDescription;
        this.contentDescriptor = contentDescriptor;
        this.documentType = documentType;
        this.owner = owner;
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "Template{" + "id=" + getId() + ", uuId=" + getUuId() + ", templateName=" 
                + getTemplateName() + ", templateLongName=" + getTemplateLongName() 
                + ", templateDescription=" + getTemplateDescription() + ", contentDescriptor=" 
                + getContentDescriptor() + ", documentType=" + getDocumentType() + ", modifiedDate=" 
                + getModifiedDate() + '}';
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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateLongName() {
        return templateLongName;
    }

    public void setTemplateLongName(String templateLongName) {
        this.templateLongName = templateLongName;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    public DocumentContentDescriptor getContentDescriptor() {
        return contentDescriptor;
    }

    public void setContentDescriptor(DocumentContentDescriptor contentDescriptor) {
        this.contentDescriptor = contentDescriptor;
    }

    public EntityType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(EntityType documentType) {
        this.documentType = documentType;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}

