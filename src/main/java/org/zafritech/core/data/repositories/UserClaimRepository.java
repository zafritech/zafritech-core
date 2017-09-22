/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.Claim;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserClaim;

/**
 *
 * @author LukeS
 */
public interface UserClaimRepository extends CrudRepository<UserClaim, Long> {
    
    List<UserClaim> findByUser(User user);
    
    List<UserClaim> findByClaim(Claim claim);
    
    UserClaim findFirstByClaim(Claim claim);
    
    UserClaim findFirstByUserAndClaim(User user, Claim claim);
    
    List<UserClaim> findByUserAndClaimClaimType(User user, ClaimType type);
}
