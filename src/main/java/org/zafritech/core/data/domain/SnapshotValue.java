package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "CORE_SNAPSHOT_VALUES")
public class SnapshotValue implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
             
    private String component;
    
    private String componentVariable;
    
    private String value;
    
    private String dataType;
    
//    @ManyToOne
//    @JoinColumn(name = "snapshot_id")
//    @JsonBackReference
//    private Snapshot snapshot;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    public SnapshotValue() {
        
        this.uuId = UUID.randomUUID().toString();
        this.creationTime = new Timestamp(System.currentTimeMillis());
    }

    public SnapshotValue(String component, String componentVariable, String value, String dataType) {
        
        this.uuId = UUID.randomUUID().toString();
        this.component = component;
        this.componentVariable = componentVariable;
        this.value = value;
        this.dataType = dataType;
        this.creationTime = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "SnapshotValue{" + "id=" + getId() + ", uuId=" + getUuId() + ", component=" + 
                getComponent() + ", componentVariable=" + getComponentVariable() + ", value=" + 
                getValue() + ", dataType=" + getDataType() + ", creationTime=" + getCreationTime() + '}';
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

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getComponentVariable() {
        return componentVariable;
    }

    public void setComponentVariable(String componentVariable) {
        this.componentVariable = componentVariable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

//    public Snapshot getSnapshot() {
//        return snapshot;
//    }
//
//    public void setSnapshot(Snapshot snapshot) {
//        this.snapshot = snapshot;
//    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}

