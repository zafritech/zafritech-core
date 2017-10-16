package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "CORE_USER_SESSION_ENTITIES")
public class UserSessionEntity implements Serializable {
    
    @EmbeddedId
    private UserSessionEntityKey sessionKey;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public UserSessionEntity() {
        
    }

    public UserSessionEntity(UserSessionEntityKey sessionKey) {
        
        this.sessionKey = sessionKey;
        this.updateDate = new Timestamp(System.currentTimeMillis());
    }

    public UserSessionEntity(UserSessionEntityKey sessionKey, Date updateDate) {
        
        this.sessionKey = sessionKey;
        this.updateDate = updateDate;
        this.updateDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        
        return "UserSessionEntity {" + "sessionKey=" + getSessionKey() + ", updateDate=" + getUpdateDate() + '}';
    }

    public UserSessionEntityKey getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(UserSessionEntityKey sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}

