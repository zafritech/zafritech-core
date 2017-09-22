/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.ClaimType;

/**
 *
 * @author LukeS
 */
public interface ClaimTypeRepository extends CrudRepository<ClaimType, Long> {
    
    @Override
    List<ClaimType> findAll();
    
    ClaimType findByTypeName(String claimType);
    
    List<ClaimType> findByEntityType(String entityType);
    
    ClaimType findFirstByTypeName(String claimType);
    
    List<ClaimType> findAllByOrderByTypeDescription();
}
