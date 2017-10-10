/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.dao;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.zafritech.requirements.enums.MediaType;

/**
 *
 * @author LukeS
 */
public class ItemDao {

    private Long id;

    private String uuId;

    private String systemId;

    @Column(columnDefinition = "TEXT")
    private String itemClass;

    private String identifier;

    private String itemNumber;

    private String itemValue;

    private Long itemTypeId;

    private MediaType mediaType;

    private Long documentId;

    private Long parentId;

    private Long refItemId;

    private String relativePosition;

    private int itemLevel;

    private int sortIndex;

    private int itemVersion;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public ItemDao() {
        
    }

    @Override
    public String toString() {
        
        return "ItemDao{" + "id=" + getId() + ", uuId=" + getUuId() + ", systemId=" + 
                getSystemId() + ", itemClass=" + getItemClass() + ", identifier=" + 
                getIdentifier() + ", itemNumber=" + getItemNumber() + ", itemValue=" + 
                getItemValue() + ", itemType=" + getItemTypeId() + ", mediaType=" + 
                getMediaType() + ", documentId=" + getDocumentId() + ", parentId=" + 
                getParentId() + ", refItemId=" + getRefItemId() + ", itemLevel=" + 
                getItemLevel() + ", sortIndex=" + getSortIndex() + ", itemVersion=" + 
                getItemVersion() + ", creationDate=" + getCreationDate() + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getRefItemId() {
        return refItemId;
    }

    public void setRefItemId(Long refItemId) {
        this.refItemId = refItemId;
    }

    public String getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(String relativePosition) {
        this.relativePosition = relativePosition;
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

    public int getItemVersion() {
        return itemVersion;
    }

    public void setItemVersion(int itemVersion) {
        this.itemVersion = itemVersion;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
