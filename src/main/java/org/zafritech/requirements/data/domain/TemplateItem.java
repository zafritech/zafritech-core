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
import javax.validation.constraints.NotNull;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.requirements.enums.MediaType;

@Entity(name = "REQUIREMENTS_TEMPLATE_ITEMS")
public class TemplateItem implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;

    private String uuId;

    @Column(nullable = false)
    private String systemId;

    @Column(columnDefinition = "TEXT")
    private String itemClass;

    private String itemNumber;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String itemValue;

    @ManyToOne
    @JoinColumn(name = "itemTypeId")
    private EntityType itemType;

    @Enumerated(EnumType.STRING)
    private MediaType mediaType;
    
    private String itemCaption;

    private String parentSysId;
    
    @ManyToOne
    @JoinColumn(name = "templateId")
    private Template template;
    
    private int itemLevel;

    private int sortIndex;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public TemplateItem() {
        
    }
    
    public TemplateItem(String systemId, 
                        String itemValue, 
                        EntityType itemType, 
                        String parentSysId,
                        Template template) {
        
        this.uuId = UUID.randomUUID().toString();
        this.systemId = systemId;
        this.itemValue = itemValue;
        this.itemType = itemType;
        this.mediaType = MediaType.TEXT;
        this.parentSysId = parentSysId;
        this.template = template;
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public TemplateItem(String systemId, 
                        String itemClass,
                        int itemLevel,
                        String itemNumber,
                        String itemValue, 
                        EntityType itemType, 
                        MediaType mediaType, 
                        String parentSysId,
                        Template template,
                        int sortIndex) {
        
        this.uuId = UUID.randomUUID().toString();
        this.systemId = systemId;
        this.itemClass = itemClass;
        this.itemLevel = itemLevel;
        this.itemLevel = itemLevel;
        this.itemNumber = itemNumber;
        this.itemValue = itemValue;
        this.itemType = itemType;
        this.mediaType = mediaType;
        this.parentSysId = parentSysId;
        this.template = template;
        this.sortIndex = sortIndex;
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "TemplateItem{" + "id=" + id + ", uuId=" + uuId + ", systemId=" 
                + systemId + ", itemClass=" + itemClass + ", itemNumber=" 
                + itemNumber + ", itemValue=" + itemValue + ", itemType=" 
                + itemType + ", mediaType=" + mediaType + ", itemCaption=" 
                + itemCaption + ", parentSysId=" + parentSysId + ", template=" 
                + template + ", itemLevel=" + itemLevel + ", sortIndex=" 
                + sortIndex + ", modifiedDate=" + modifiedDate + '}';
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

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getItemClass() {
        return itemClass;
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public EntityType getItemType() {
        return itemType;
    }

    public void setItemType(EntityType itemType) {
        this.itemType = itemType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getItemCaption() {
        return itemCaption;
    }

    public void setItemCaption(String itemCaption) {
        this.itemCaption = itemCaption;
    }

    public String getParentSysId() {
        return parentSysId;
    }

    public void setParentSysId(String parentSysId) {
        this.parentSysId = parentSysId;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}

