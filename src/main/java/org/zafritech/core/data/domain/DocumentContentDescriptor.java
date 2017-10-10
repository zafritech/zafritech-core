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

    @Column(columnDefinition = "TEXT")
    private String description;

    public DocumentContentDescriptor() {
        
    }

    public DocumentContentDescriptor(String descriptorName, 
                                     String descriptorCode) {
        
        this.uuId = UUID.randomUUID().toString();
        this.descriptorName = descriptorName;
        this.descriptorCode = descriptorCode;
    }

    public DocumentContentDescriptor(String descriptorName,
                                     String descriptorCode,
                                     String componentName) {
        
        this.uuId = UUID.randomUUID().toString();
        this.descriptorName = descriptorName;
        this.descriptorCode = descriptorCode;
        this.componentName = componentName;
    }

    public DocumentContentDescriptor(String descriptorName, 
                                     String componentName,
                                     String descriptorCode, 
                                     String description) {
        
        this.uuId = UUID.randomUUID().toString();
        this.descriptorName = descriptorName;
        this.descriptorCode = descriptorCode;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }
}

