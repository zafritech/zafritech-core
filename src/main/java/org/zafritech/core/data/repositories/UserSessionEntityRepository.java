/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.UserSessionEntity;
import org.zafritech.core.data.domain.UserSessionEntityKey;
import org.zafritech.core.enums.UserSessionEntityTypes;

/**
 *
 * @author LukeS
 */
public interface UserSessionEntityRepository extends CrudRepository<UserSessionEntity, UserSessionEntityKey> {
    
    UserSessionEntity findBySessionKey(UserSessionEntityKey sessionKey);
    
    List<UserSessionEntity> findBySessionKeyUserIdAndSessionKeyEntityType(Long userId, UserSessionEntityTypes entityType);
    
    UserSessionEntity findFirstBySessionKeyUserIdAndSessionKeyEntityTypeOrderByUpdateDateDesc(Long userId, UserSessionEntityTypes entityType);
    
    List<UserSessionEntity> findBySessionKeyUserIdAndSessionKeyEntityTypeOrderByUpdateDateDesc(Long userId, UserSessionEntityTypes entityType);
}
