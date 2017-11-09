/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import org.zafritech.core.data.domain.Claim;
import org.zafritech.core.data.domain.User;

/**
 *
 * @author LukeS
 */
public interface AccessControlService {
    
    boolean isAuthorized(String permission);
    
    boolean hasRole(User user, String roleName);
    
    boolean hasClaim(User user, Claim claim);
    
    boolean hasClaims(User user, Claim[] claims);
}
