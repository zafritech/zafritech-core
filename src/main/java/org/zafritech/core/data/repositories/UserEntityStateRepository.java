/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.UserEntityState;
import org.zafritech.core.data.domain.UserEntityStateKey;
import org.zafritech.core.enums.UserEntityTypes;

/**
 *
 * @author LukeS
 */
public interface UserEntityStateRepository extends CrudRepository<UserEntityState, UserEntityStateKey> {
    
    UserEntityState findByStateKey(UserEntityStateKey stateKey);
    
    List<UserEntityState> findByStateKeyUserIdAndStateKeyEntityType(Long userId, UserEntityTypes entityType);
    
    List<UserEntityState> findByStateKeyUserIdAndStateKeyEntityTypeOrderByUpdateDateDesc(Long userId, UserEntityTypes entityType);
}
