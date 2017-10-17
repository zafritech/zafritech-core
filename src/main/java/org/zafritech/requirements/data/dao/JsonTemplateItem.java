/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.dao;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author LukeS
 */
@JsonPropertyOrder({"systemId", "itemClass", "itemNumber", "itemValue",
    "itemType", "mediaType", "parentSystemId", "itemLevel", "sortIndex"})
public class JsonTemplateItem {
    
    private String systemId;

    private String itemClass;

    private String itemNumber;

    private String itemValue;

    private String itemType;

    private String mediaType;
    
    private String parentSystemId;
    
    private int itemLevel;

    private int sortIndex;

    public JsonTemplateItem() {
        
    }

    public JsonTemplateItem(String systemId, 
                                 String itemClass, 
                                 String itemNumber, 
                                 String itemValue, 
                                 String itemType, 
                                 String mediaType, 
                                 String parentSystemId, 
                                 int itemLevel, 
                                 int sortIndex) {
        
        this.systemId = systemId;
        this.itemClass = itemClass;
        this.itemNumber = itemNumber;
        this.itemValue = itemValue;
        this.itemType = itemType;
        this.mediaType = mediaType;
        this.parentSystemId = parentSystemId;
        this.itemLevel = itemLevel;
        this.sortIndex = sortIndex;
    }

    @Override
    public String toString() {
        
        return "TemplateItemToJsonDao{" + "systemId=" + getSystemId() + ", itemClass=" 
                + getItemClass() + ", itemNumber=" + getItemNumber() + ", itemValue=" 
                + getItemValue() + ", itemType=" + getItemType() + ", mediaType=" + getMediaType() 
                + ", parentSystemId=" + getParentSystemId() + ", itemLevel=" 
                + getItemLevel() + ", sortIndex=" + getSortIndex() + '}';
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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getParentSystemId() {
        return parentSystemId;
    }

    public void setParentSystemId(String parentSystemId) {
        this.parentSystemId = parentSystemId;
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
}
