/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.zafritech.core.data.domain.Claim;
import org.zafritech.core.data.domain.ClaimType;

/**
 *
 * @author LukeS
 */
public interface ClaimRepository extends PagingAndSortingRepository<Claim, Long> {
    
//    List<Claim> findByUser(User user);
    
//    List<Claim> findByUserAndClaimType(User user, ClaimType claimType);
    
//    List<Claim> findByUserAndClaimTypeAndClaimValue(User user, ClaimType claimType, Long claimValue);
    
    List<Claim> findByClaimTypeAndClaimValue(ClaimType claimType, Long claimValue);
    
    Claim findFirstByClaimTypeAndClaimValue(ClaimType claimType, Long claimValue);
}
