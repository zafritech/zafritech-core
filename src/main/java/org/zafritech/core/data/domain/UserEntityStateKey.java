/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.zafritech.core.enums.UserEntityTypes;

/**
 *
 * @author LukeS
 */
@Embeddable
public class UserEntityStateKey implements Serializable {
    
    @Column(name = "userId", nullable = false)
    private Long userId;
    
    @Column(name = "entityType", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserEntityTypes entityType;

    @Column(name = "entityId", nullable = false)
    private Long entityId;

    public UserEntityStateKey() {
        
    }

    public UserEntityStateKey(Long userId, UserEntityTypes entityType, Long entityId) {
        this.userId = userId;
        this.entityType = entityType;
        this.entityId = entityId;
    }

    @Override
    public String toString() {
        
        return "UserEntityStateKey{" + "userId=" + userId + ", entityType=" 
                + entityType + ", entityId=" + entityId + '}';
    }

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

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
