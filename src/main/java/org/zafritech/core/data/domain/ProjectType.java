package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "CORE_PROJECT_TYPES")
public class ProjectType implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    private String typeCode;
    
    private String typeName;
    
    private String typeDescription;

    public ProjectType() {
        
    }

    @Override
    public String toString() {
        
        return "ProjectType{" + "id=" + id + ", uuId=" + uuId + ", typeCode=" + 
                typeCode + ", typeName=" + typeName + ", typeDescription=" + typeDescription + '}';
    }

    public ProjectType(String typeCode, String typeName) {
        
        this.uuId = UUID.randomUUID().toString();
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public ProjectType(String typeCode, String typeName, String typeDescription) {
        
        this.uuId = UUID.randomUUID().toString();
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.typeDescription = typeDescription;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }
}

