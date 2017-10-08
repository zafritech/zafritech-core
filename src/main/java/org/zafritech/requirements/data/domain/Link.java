package org.zafritech.requirements.data.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import org.zafritech.core.data.domain.Document;

@Entity(name = "REQUIREMENTS_ITEM_LINKS")
public class Link implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    @ManyToOne
    @JoinColumn(name = "linkGroupId")
    private LinkGroup linkGroup;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "srcItemId")
    private Item srcItem;
    
    @ManyToOne
    @JoinColumn(name = "srcDocumentId")
    private Document srcDocument;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "dstItemId")
    private Item dstItem;
    
    @ManyToOne
    @JoinColumn(name = "dstDocumentId")
    private Document dstDocument;
    
    @ManyToOne
    @JoinColumn(name = "linkTypeId")
    private LinkType linkType;
    
    @Column(columnDefinition = "TEXT")
    private String linkComment;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    private boolean dstItemChanged;
    
    @Column(columnDefinition = "TEXT")
    private String dstHistoryValue;

    public Link() {
        
    }

    public Link(Item srcItem, 
                Item dstItem, 
                LinkType linkType) {
        
        this.uuId = UUID.randomUUID().toString();
        this.srcItem = srcItem;
        this.dstItem = dstItem;
        this.linkType = linkType;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.dstItemChanged = false;
    }

    public Link(Item srcItem, 
                Document srcDocument, 
                Item dstItem, 
                Document dstDocument, 
                LinkType linkType) {
        
        this.uuId = UUID.randomUUID().toString();
        this.srcItem = srcItem;
        this.srcDocument = srcDocument;
        this.dstItem = dstItem;
        this.dstDocument = dstDocument;
        this.linkType = linkType;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.dstItemChanged = false;
    }

    public Link(Item srcItem, 
                Document srcDocument, 
                Item dstItem, 
                Document dstDocument, 
                LinkType linkType,
                String linkComment) {
        
        this.uuId = UUID.randomUUID().toString();
        this.srcItem = srcItem;
        this.srcDocument = srcDocument;
        this.dstItem = dstItem;
        this.dstDocument = dstDocument;
        this.linkType = linkType;
        this.linkComment = linkComment;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.dstItemChanged = false;
    }

    @Override
    public String toString() {
        
        return "Link{" + "id=" + getId() + ", uuId=" + 
                getUuId() + ", srcItem=" + getSrcItem() + ", srcDocument=" + 
                getSrcDocument() + ", dstItem=" + getDstItem() + ", dstDocument=" + 
                getDstDocument() + ", linkType=" + getLinkType() + ", creationDate=" + 
                getCreationDate() + ", dstItemChanged=" + 
                isDstItemChanged() + ", dstHistoryValue=" + getDstHistoryValue() + '}';
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

    public LinkGroup getLinkGroup() {
        return linkGroup;
    }

    public void setLinkGroup(LinkGroup linkGroup) {
        this.linkGroup = linkGroup;
    }

    public Item getSrcItem() {
        return srcItem;
    }

    public void setSrcItem(Item srcItem) {
        this.srcItem = srcItem;
    }

    public Document getSrcDocument() {
        return srcDocument;
    }

    public void setSrcDocument(Document srcDocument) {
        this.srcDocument = srcDocument;
    }

    public Item getDstItem() {
        return dstItem;
    }

    public void setDstItem(Item dstItem) {
        this.dstItem = dstItem;
    }

    public Document getDstDocument() {
        return dstDocument;
    }

    public void setDstDocument(Document dstDocument) {
        this.dstDocument = dstDocument;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public void setLinkType(LinkType linkType) {
        this.linkType = linkType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isDstItemChanged() {
        return dstItemChanged;
    }

    public void setDstItemChanged(boolean dstItemChanged) {
        this.dstItemChanged = dstItemChanged;
    }

    public String getDstHistoryValue() {
        return dstHistoryValue;
    }

    public void setDstHistoryValue(String dstHistoryValue) {
        this.dstHistoryValue = dstHistoryValue;
    }
}

