package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "CORE_DOCUMENT_CONTENT_DESCRIPTORS")
public class DocumentContentDescriptor implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;

    @Column(nullable = false)
    private String descriptorName;

    @Column(unique = true, nullable = false)
    private String descriptorCode;
    
    private String componentName;
    
    private String urlPathString;

    @Column(columnDefinition = "TEXT")
    private String description;

    public DocumentContentDescriptor() {
        
    }

    public DocumentContentDescriptor(String descriptorName, 
                                     String descriptorCode,
                                     String urlPathString) {
        
        this.uuId = UUID.randomUUID().toString();
        this.descriptorName = descriptorName;
        this.descriptorCode = descriptorCode;
        this.urlPathString = urlPathString;
    }

    public DocumentContentDescriptor(String descriptorName,
                                     String descriptorCode,
                                     String urlPathString,
                                     String componentName) {
        
        this.uuId = UUID.randomUUID().toString();
        this.descriptorName = descriptorName;
        this.descriptorCode = descriptorCode;
        this.componentName = componentName;
        this.urlPathString = urlPathString;
    }

    public DocumentContentDescriptor(String descriptorName, 
                                     String componentName,
                                     String descriptorCode,
                                     String urlPathString, 
                                     String description) {
        
        this.uuId = UUID.randomUUID().toString();
        this.descriptorName = descriptorName;
        this.descriptorCode = descriptorCode;
        this.urlPathString = urlPathString;
        this.description = description;
        this.componentName = componentName;
    }

    @Override
    public String toString() {
        
        return "DocumentTypeDescriptor{" + "id=" + getId() + ", uuId=" + 
                getUuId() + ", descriptorName=" + getDescriptorName() + ", descriptorCode=" + 
                getDescriptorCode() + ", description=" + getDescription() + '}';
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

    public String getDescriptorName() {
        return descriptorName;
    }

    public void setDescriptorName(String descriptorName) {
        this.descriptorName = descriptorName;
    }

    public String getDescriptorCode() {
        return descriptorCode;
    }

    public void setDescriptorCode(String descriptorCode) {
        this.descriptorCode = descriptorCode;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getUrlPathString() {
        return urlPathString;
    }

    public void setUrlPathString(String urlPathString) {
        this.urlPathString = urlPathString;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

