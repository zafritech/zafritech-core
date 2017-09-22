package org.zafritech.requirements.data.domain;

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

@Entity(name = "REQUIREMENTS_ITEM_HISTORY")
public class ItemHistory implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;

    private String systemId;

    @Column(columnDefinition = "TEXT")
    private String itemValue;
        
    private int itemVersion;
    
    private String uuId;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public ItemHistory() {
        
    }

    public ItemHistory(Item item, String sysId, String itemValue, int itemVersion) {
        
        this.item = item;
        this.systemId = sysId;
        this.itemValue = itemValue;
        this.itemVersion = itemVersion;
        this.uuId = UUID.randomUUID().toString();
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "ItemHistory{" + "id=" + getId() + ", item=" + 
                getItem() + ", sysId=" + getSystemId() + ", itemValue=" + 
                getItemValue() + ", itemVersion=" + getItemVersion() + ", uuId=" + 
                getUuId() + ", creationDate=" + getCreationDate() + '}';
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public int getItemVersion() {
        return itemVersion;
    }

    public void setItemVersion(int itemVersion) {
        this.itemVersion = itemVersion;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

