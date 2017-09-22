/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.Role;
import org.zafritech.core.data.repositories.RoleRepository;

/**
 *
 * @author LukeS
 */
@Component
public class UserRolesInit {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Transactional
    public void init() {
        
        roleRepository.save(new Role("ROLE_ADMIN", "Administrator"));
        roleRepository.save(new Role("ROLE_MANAGER", "Manager"));
        roleRepository.save(new Role("ROLE_USER", "Application User"));
        roleRepository.save(new Role("ROLE_ACTUATOR", "System Actuator"));
        roleRepository.save(new Role("ROLE_GUEST", "Guest User"));
    }
}
