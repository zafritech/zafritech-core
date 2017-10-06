package org.zafritech.core.data.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.zafritech.core.enums.UserEntityTypes;

@Embeddable
public class UserStateKey implements Serializable {
    
    @Column(name = "userId", nullable = false)
    private Long userId;
    
    @Column(name = "entityType", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserEntityTypes entityType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserEntityTypes getEntityType() {
        return entityType;
    }

    public void setEntityType(UserEntityTypes entityType) {
        this.entityType = entityType;
    }
}

