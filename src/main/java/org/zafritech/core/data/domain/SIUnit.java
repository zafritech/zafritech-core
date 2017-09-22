package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "CORE_SI_UNITS")
public class SIUnit implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;
    
    private String symbol;
    
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String definition;

    public SIUnit() {
        
    }

    public SIUnit(String symbol, String name, String definition) {
        
        this.uuId = UUID.randomUUID().toString();
        this.symbol = symbol;
        this.name = name;
        this.definition = definition;
    }

    @Override
    public String toString() {
        
        return "SIUnit{" + "id=" + getId() + ", uuId=" 
                + getUuId() + ", symbol=" + getSymbol() + ", name=" 
                + getName() + ", definition=" + getDefinition() + '}';
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}

