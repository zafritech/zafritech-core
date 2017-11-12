/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import java.util.HashSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.zafritech.core.data.domain.Role;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.repositories.RoleRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.services.impl.UserServiceImpl;

/**
 *
 * @author LukeS
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    
    public UserServiceTest() {
        
    }
    
    @TestConfiguration
    static class UserServiceConfiguration {
        
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }
    
    @Autowired
    private UserService userService;      
    
    @MockBean
    private UserRepository userRepository;
    
    @MockBean
    private RoleRepository roleRepository;
    
    @Before
    public void setUp() {
        
        HashSet<Role> adminRoles = new HashSet<Role>() {
            {
                add(new Role("ROLE_ADMIN"));
                add(new Role("ROLE_MANAGER"));
                add(new Role("ROLE_USER"));
            }
        };
        
        HashSet<Role> userRoles = new HashSet<Role>() {{ add(new Role("ROLE_USER")); }};
        
        User admin = new User("admin@domain.org", "Password@123", adminRoles);
        User user = new User("user@domain.org", "Password@123", userRoles);
        
        Mockito.when(userRepository.findByEmail("admin@domain.org")).thenReturn(admin); 
        Mockito.when(userRepository.findByEmail("user@domain.org")).thenReturn(user); 
    }
    
    @After
    public void tearDown() {
        
    }

    @Test
    public void whenValidUserThenUserShouldBeFound() {
        
        String email = "admin@domain.org";
        
        User foundUser = userService.findByEmail(email);
        assertEquals(foundUser.getEmail(), email);
    }
            
    @Test
    public void whenUserWithValidRolesThenUserRoleShouldBeFound() {
    
        User adminUser = userService.findByEmail("admin@domain.org");
        assertTrue(userService.hasRole("ROLE_ADMIN", adminUser)); 
    }
            
    @Test
    public void whenUserWithInvalidRoleThenUserRoleNotFound() {
    
        User normalUser = userService.findByEmail("user@domain.org");
        assertFalse(userService.hasRole("ROLE_ADMIN", normalUser)); 
    }
    
}
