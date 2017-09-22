/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.SystemVariable;

/**
 *
 * @author LukeS
 */
public interface SystemVariableRepository extends CrudRepository<SystemVariable, Long> {
    
    List<SystemVariable> findByOwnerIdAndOwnerTypeAndVariableName(Long id, String ownerType, String name);

    List<SystemVariable> findByOwnerIdAndOwnerTypeAndVariableNameOrderByVariableValue(Long id, String ownerType, String name);
}
