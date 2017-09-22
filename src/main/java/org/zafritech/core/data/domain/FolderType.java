package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "CORE_FOLDER_TYPES")
public class FolderType implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    private String typeName;
    
    @Column(unique = true, nullable = false)
    private String typeKey;
    
    private String typeDescription;

    public FolderType() {
        
    }

    public FolderType(String typeName, String typeKey) {
        
        this.uuId = UUID.randomUUID().toString();
        this.typeName = typeName;
        this.typeKey = typeKey;
    }

    public FolderType(String typeName, String typeKey, String typeDescription) {
        
        this.uuId = UUID.randomUUID().toString();
        this.typeName = typeName;
        this.typeKey = typeKey;
        this.typeDescription = typeDescription;
    }

    @Override
    public String toString() {
        
        return "FolderType{" + "id=" + id + ", uuId=" + uuId + ", typeName=" + 
                typeName + ", typeKey=" + typeKey + ", typeDescription=" + 
                typeDescription + '}';
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }
}

