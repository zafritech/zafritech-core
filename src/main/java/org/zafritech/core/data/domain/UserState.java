package org.zafritech.core.data.domain;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name = "CORE_USER_STATES")
public class UserState implements Serializable {
    
    @EmbeddedId
    private UserStateKey userStateKey;
    
    private String entityIdValues;

    public UserStateKey getUserStateKey() {
        return userStateKey;
    }

    public void setUserStateKey(UserStateKey userStateKey) {
        this.userStateKey = userStateKey;
    }

    public String getEntityIdValues() {
        return entityIdValues;
    }

    public void setEntityIdValues(String entityIdValues) {
        this.entityIdValues = entityIdValues;
    }
}

