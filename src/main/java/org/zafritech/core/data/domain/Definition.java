package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.core.enums.DefinitionTypes;

@Entity(name = "CORE_DEFINITIONS")
public class Definition implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    private String term;

    @Column(columnDefinition = "TEXT")
    private String termDefinition;
    
    @Enumerated(EnumType.STRING)
    private DefinitionTypes definitionType;
    
    @ManyToOne
    @JoinColumn(name = "localeId")
    private Locale language;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public Definition() {
        
    }

    public Definition(String term, String termDefinition, DefinitionTypes type) {
        
        this.uuId = UUID.randomUUID().toString();
        this.term = term;
        this.termDefinition = termDefinition;
        this.definitionType = type;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public Definition(String term, String termDefinition, DefinitionTypes type, Locale language) {
        
        this.uuId = UUID.randomUUID().toString();
        this.term = term;
        this.termDefinition = termDefinition;
        this.definitionType = type;
        this.language = language;
        this.creationDate = new Timestamp(System.currentTimeMillis());
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

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTermDefinition() {
        return termDefinition;
    }

    public void setTermDefinition(String termDefinition) {
        this.termDefinition = termDefinition;
    }

    public DefinitionTypes getDefinitionType() {
        return definitionType;
    }

    public void setDefinitionType(DefinitionTypes definitionType) {
        this.definitionType = definitionType;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

