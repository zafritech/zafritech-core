package org.zafritech.requirements.data.domain;

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
import org.zafritech.requirements.enums.ItemStatus;

@Entity(name = "REQUIREMENTS_VERIFICATION_REFERENCES")
public class VerificationReference implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;
    
    @ManyToOne
    @JoinColumn(name = "methodId")
    private VerificationMethod method;
    
    @Column(columnDefinition = "TEXT")
    private String verificationRef;
    
    @Column(columnDefinition = "TEXT")
    private String evidence;
    
    @Enumerated(EnumType.STRING)
    private ItemStatus status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public VerificationReference() {
        
    }

    public VerificationReference(Item item, VerificationMethod method) {
        
        this.uuId = UUID.randomUUID().toString();
        this.item = item;
        this.method = method;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public VerificationReference(Item item, VerificationMethod method, String reference) {
        
        this.uuId = UUID.randomUUID().toString();
        this.item = item;
        this.method = method;
        this.verificationRef = reference;
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "VerificationReference{" + "id=" + getId() + ", uuId=" + 
                getUuId() + ", item=" + getItem() + ", method=" + getMethod() + ", verificationRef=" + 
                getReferences() + ", evidence=" + getEvidence() + ", status=" + 
                getStatus() + ", creationDate=" + getCreationDate() + '}';
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public VerificationMethod getMethod() {
        return method;
    }

    public void setMethod(VerificationMethod method) {
        this.method = method;
    }

    public String getReferences() {
        return verificationRef;
    }

    public void setReferences(String references) {
        this.verificationRef = references;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

