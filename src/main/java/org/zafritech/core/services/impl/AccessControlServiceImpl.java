/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import org.springframework.stereotype.Service;
import org.zafritech.core.data.domain.Claim;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.services.AccessControlService;

/**
 *
 * @author LukeS
 */
@Service
public class AccessControlServiceImpl implements AccessControlService {

    @Override
    public boolean hasRole(User user, String roleName) {
        
        return true;
    }

    @Override
    public boolean isAuthorized(String permission) {

        return false;
    }

    @Override
    public boolean hasClaim(User user, Claim claim) {

        return false;
    }

    @Override
    public boolean hasClaims(User user, Claim[] claims) {

        return false;
    }
}
