/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.api.core;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.core.data.dao.ContactDao;
import org.zafritech.core.data.dao.PwdDao;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.Contact;
import org.zafritech.core.data.domain.Role;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.projections.UserView;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.RoleRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.services.ContactService;
import org.zafritech.core.services.UserService;

/**
 *
 * @author LukeS
 */
@RestController
public class UserAdminRestController {
      
    @Autowired
    private UserService userService;
 
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
     
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
    
    @RequestMapping(value = "/api/admin/users/user/list", method = GET)
    public ResponseEntity<List<UserView>> listAllUsers() {
        
        List<User> users = new ArrayList<>();
        userRepository.findAllByOrderByFirstNameAsc().forEach(users::add); 
        
        List<UserView> members = new ArrayList<>();
        
        for (User user : users) {
            
            members.add(userRepository.findUserViewByEmail(user.getEmail()));
        }
        
        return new ResponseEntity<List<UserView>>(members, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/user/password/reset", method = POST)
    public ResponseEntity<UserView> getUserAdminRestPwd(@RequestBody PwdDao pwdDao) {
        
        User user = userService.findByUuId(pwdDao.getUuId()); 

        if (user != null && pwdDao.getNewPassword().equals(pwdDao.getNewPasswordConfirm())) {
            
            userService.changePasswordTo(user, pwdDao.getNewPassword());
            
        } else {
            
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        UserView userView = userRepository.findUserViewByEmail(user.getEmail());
        
        return new ResponseEntity<UserView>(userView, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/users/delete/{uuid}", method = GET)
    public ResponseEntity<Long> deleteUser(@PathVariable(value = "uuid") String uuid) {
        
        User user = userService.findByUuId(uuid);
        Long userId = user.getId();
        userRepository.delete(user); 

        return new ResponseEntity<Long>(userId, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/users/roles/list", method = GET)
    public ResponseEntity<List<Role>> findAllRoles() {
        
        List<Role> roles = (List<Role>) roleRepository.findAll(); 

        return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/users/contact/{uuid}", method = GET)
    public ResponseEntity<Contact> getUserContacts(@PathVariable(value = "uuid") String uuid) {
        
        User user = userService.findByUuId(uuid); 
        Contact contact = user.getContact();
        
        if (contact == null) {
            
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
                
        return new ResponseEntity<Contact>(contact, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/users/contact/save/{uuid}", method = POST)
    public ResponseEntity<Contact> updateCountriesData(@PathVariable(value = "uuid") String uuid,
                                                       @RequestBody ContactDao dao) {

        User user = userService.findByUuId(uuid);
        Contact contact = contactService.getUserContactFromDao(user, dao);
                
        return new ResponseEntity<Contact>(contact, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/users/claims/types/list", method = GET)
    public ResponseEntity<List<ClaimType>> findAllClaimTypes() {
        
        List<ClaimType> claimTypes = (List<ClaimType>) claimTypeRepository.findAllByOrderByTypeDescription(); 

        return new ResponseEntity<List<ClaimType>>(claimTypes, HttpStatus.OK);
    }
}
