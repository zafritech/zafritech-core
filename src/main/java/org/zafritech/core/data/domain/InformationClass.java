package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "CORE_INFORMATION_CLASS")
public class InformationClass implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    @Column(nullable = false)
    private String className;
    
    @Column(unique = true, nullable = false)
    private String classCode;
    
    private String classDescription;

    public InformationClass() {
        
    }

    @Override
    public String toString() {
        
        return "InformationClass{" + "id=" + id + ", uuId=" + 
                uuId + ", className=" + className + ", classCode=" + 
                classCode + ", classDescription=" + classDescription + '}';
    }

    public InformationClass(String className, String classCode) {
        
        this.uuId = UUID.randomUUID().toString();
        this.className = className;
        this.classCode = classCode;
    }

    public InformationClass(String className, String classCode, String classDescription) {
        
        this.uuId = UUID.randomUUID().toString();
        this.className = className;
        this.classCode = classCode;
        this.classDescription = classDescription;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }
}

