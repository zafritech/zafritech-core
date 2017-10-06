package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "CORE_USER_ENTITY_STATES")
public class UserEntityState implements Serializable {
    
    @EmbeddedId
    private UserEntityStateKey stateKey;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public UserEntityState() {
        
    }

    public UserEntityState(UserEntityStateKey stateKey) {
        
        this.stateKey = stateKey;
        this.updateDate = new Timestamp(System.currentTimeMillis());
    }

    public UserEntityState(UserEntityStateKey stateKey, Date updateDate) {
        
        this.stateKey = stateKey;
        this.updateDate = updateDate;
        this.updateDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "UserEntityState{" + "stateKey=" + stateKey + ", updateDate=" + updateDate + '}';
    }

    public UserEntityStateKey getStateKey() {
        return stateKey;
    }

    public void setStateKey(UserEntityStateKey stateKey) {
        this.stateKey = stateKey;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}

