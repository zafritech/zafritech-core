package org.zafritech.requirements.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "REQUIREMENTS_ITEM_TYPES")
public class ItemType implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;

    private String itemTypeName;

    private String itemTypeLongName;

    private String itemTypeCode;

    @Column(columnDefinition = "TEXT")
    private String itemTypeDescription;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
    public ItemType() {
        super();
    }

    public ItemType(String itemTypeName) {
        super();
        this.itemTypeName = itemTypeName;
    }

    @Override
    public String toString() {
        return "Item Type {"
                + "ID: " + getId()
                + ", Item Type Name = '" + getItemTypeName() + '\''
                + ", Item Type Long Name = '" + getItemTypeLongName() + '\''
                + ", Item Type Description = '" + getItemTypeDescription() + '\''
                + '}';
    }

    public ItemType(String itemTypeName, String itemTypeLongName, String itemTypeDescription) {

        this.uuId = UUID.randomUUID().toString();
        this.itemTypeName = itemTypeName;
        this.itemTypeLongName = itemTypeLongName;
        this.itemTypeCode = itemTypeName.substring(0, 3); 
        this.itemTypeDescription = itemTypeDescription;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public ItemType(String itemTypeName,
                    String itemTypeCode, 
                    String itemTypeLongName, 
                    String itemTypeDescription) {

        this.uuId = UUID.randomUUID().toString();
        this.itemTypeName = itemTypeName;
        this.itemTypeLongName = itemTypeLongName;
        this.itemTypeCode = itemTypeCode;
        this.itemTypeDescription = itemTypeDescription;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.modifiedDate = new Timestamp(System.currentTimeMillis());
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

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public String getItemTypeLongName() {
        return itemTypeLongName;
    }

    public void setItemTypeLongName(String itemTypeLongName) {
        this.itemTypeLongName = itemTypeLongName;
    }

    public String getItemTypeCode() {
        return itemTypeCode;
    }

    public void setItemTypeCode(String itemTypeCode) {
        this.itemTypeCode = itemTypeCode;
    }

    public String getItemTypeDescription() {
        return itemTypeDescription;
    }

    public void setItemTypeDescription(String itemTypeDescription) {
        this.itemTypeDescription = itemTypeDescription;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}

