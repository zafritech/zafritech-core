package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "CORE_LOCALES")
public class Locale implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    private String code;
    
    private String name;

    public Locale() {
        
    }

    public Locale(String code, String name) {
        
        this.uuId = UUID.randomUUID().toString();
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        
        return "Locale = {" + "id=" + id + ", uuId=" + uuId + ", code=" + code + ", name=" + name + '}';
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

