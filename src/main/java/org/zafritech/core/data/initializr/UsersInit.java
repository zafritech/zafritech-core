/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.dao.UserDao;
import org.zafritech.core.data.domain.Contact;
import org.zafritech.core.data.domain.Role;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.repositories.ContactRepository;
import org.zafritech.core.data.repositories.CountryRepository;
import org.zafritech.core.data.repositories.RoleRepository;
import org.zafritech.core.data.repositories.UserRepository;

/**
 *
 * @author LukeS
 */
@Component
public class UsersInit {
    
    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    private ContactRepository contactRepository;
     
    @Transactional
    public void init() {
        
        String file = data_dir + "initialisation/users.json";
  
        ObjectMapper mapper = new ObjectMapper();
        
        Set<Role> userRoles = new HashSet<Role>() {{ add(roleRepository.findByRoleName("ROLE_USER")); }};
        Set<Role> guestRoles = new HashSet<Role>() {{ add(roleRepository.findByRoleName("ROLE_GUEST")); }};
        Set<Role> adminRoles = new HashSet<Role>() {
            {
                add(roleRepository.findByRoleName("ROLE_ACTUATOR"));
                add(roleRepository.findByRoleName("ROLE_ADMIN"));
                add(roleRepository.findByRoleName("ROLE_MANAGER"));
                add(roleRepository.findByRoleName("ROLE_USER"));
            }
        };
        
        Set<Role> superRoles = new HashSet<Role>() {
            {
                add(roleRepository.findByRoleName("ROLE_ADMIN"));
                add(roleRepository.findByRoleName("ROLE_MANAGER"));
                add(roleRepository.findByRoleName("ROLE_USER"));
            }
        };
          
        try {
            
            List<UserDao> jsonUsers = Arrays.asList(mapper.readValue(new File(file), UserDao[].class));
            
            for (UserDao jsonUser : jsonUsers) {
            
                User user = new User(jsonUser.getEmail(), jsonUser.getFirstName(), jsonUser.getLastName(), jsonUser.getMainRole());
                
                Contact contact = new Contact(
                
                        jsonUser.getContact().getEmail(), 
                        jsonUser.getContact().getFirstName(), 
                        jsonUser.getContact().getLastName(), 
                        jsonUser.getContact().getAddress(), 
                        jsonUser.getContact().getCity(), 
                        jsonUser.getContact().getState(), 
                        countryRepository.findByIso3(jsonUser.getContact().getCountry()), 
                        jsonUser.getContact().getPostCode(), 
                        jsonUser.getContact().getPhone(), 
                        jsonUser.getContact().getMobile(), 
                        jsonUser.getContact().getWebsite()
                );
                
                contact = contactRepository.save(contact);
                user.setContact(contact);
                 
                
                switch(jsonUser.getEmail()) {

                    case "admin@zidingo.org":

                        user.setUserRoles(adminRoles);
                        user.setPassword(new BCryptPasswordEncoder().encode("admin"));
                        break;
                        
                    case "superuser@zidingo.org":

                        user.setUserRoles(superRoles);
                        user.setPassword(new BCryptPasswordEncoder().encode("super"));
                        break;

                    case "guest@zidingo.org":

                        user.setUserRoles(guestRoles);
                        user.setPassword(new BCryptPasswordEncoder().encode("guest"));
                        break;

                    default:
                        user.setUserRoles(userRoles);
                        user.setPassword(new BCryptPasswordEncoder().encode("Password@123"));
                }
              
                user = userRepository.save(user);
                
                String msg = "User data initialisation: " + user.toString();
                Logger.getLogger(UsersInit.class.getName()).log(Level.INFO, msg);
            }
            
        } catch (IOException ex) {
            
            Logger.getLogger(UsersInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
