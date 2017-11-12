/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import java.util.List;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.dao.ClaimDao;
import org.zafritech.core.data.dao.UserDao;
import org.zafritech.core.data.domain.UserClaim;

/**
 *
 * @author LukeS
 */
public interface UserService {
    
    User loggedInUser();
    
    boolean hasRole(String roleName);
    
    boolean hasRole(String roleName, User user);
       
    List<User> allUser();
    
    User findById(Long id);

    User findByUserName(String name);

    User findByEmail(String name);

    List<User> findAll();

    List<User> findOrderByFirstName();
    
    List<User> findOrderByFirstName(int pageSize, int pageNumber);

    User findByUuId(String uuid);

    User saveUser(User user);

    User saveDao(UserDao user);

    void deleteUser(Long id);

    boolean passwordAndConfirmationMatch(UserDao user);

    boolean userExists(String email);
    
    boolean passwordMatches(String rawPassword, String encodedPassword);
    
    User changePasswordTo(User user, String password);
    
    UserClaim createClaim(ClaimDao claimDao);
    
    List<UserClaim> findUserClaims(User user);
    
    List<UserClaim> findUserClaims(User user, String claimType);
}
