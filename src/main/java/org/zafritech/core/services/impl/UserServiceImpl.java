/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.converters.DaoToUserConverter;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.Role;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.dao.ClaimDao;
import org.zafritech.core.data.dao.UserDao;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.UserClaimRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.services.ClaimService;
import org.zafritech.core.services.UserService;

/**
 *
 * @author LukeS
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DaoToUserConverter daoToUser;
    
    @Autowired
    private UserClaimRepository userClaimRepository;
    
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
    
    @Autowired
    private ClaimService claimService;
    
    @Override 
    public List<User> allUser() {
        
        return new ArrayList(userRepository.findAll());
    }
    
    @Override
    public User loggedInUser() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String name = userDetails.getUsername();
        
        User user = userRepository.findByUserName(name);

        return user;
    }
 
    @Override
    public boolean hasRole(String roleName) {
        
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) return false;
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return false;
        
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            
            if (roleName.equals(auth.getAuthority())) return true;
        }
        
        return false;
    }
    
    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();
        List<User> sanitizedUsers = new ArrayList<>();

        userRepository.findAll().forEach(users::add);
        
        users.forEach(user->{
            
            user.setUserName(null); 
            user.setPassword(null); 
            sanitizedUsers.add(user);
            
        });

        return sanitizedUsers;
    }

    @Override
    public List<User> findOrderByFirstName() {

        List<User> users = new ArrayList<>();
        List<User> sanitizedUsers = new ArrayList<>();

        userRepository.findAllByOrderByFirstNameAsc().forEach(users::add);
        
        users.forEach(user->{
            
            user.setUserName(null); 
            user.setPassword(null); 
            sanitizedUsers.add(user);
            
        });

        return sanitizedUsers;
    }
    
    @Override
    public List<User> findOrderByFirstName(int pageSize, int pageNumber) {
        
        List<User> users = new ArrayList<>();
        List<User> sanitizedUsers = new ArrayList<>();
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "firstName");
        
        userRepository.findAll(request).forEach(users::add);
        
        users.forEach(user->{
            
            user.setUserName(null); 
            user.setPassword(null); 
            sanitizedUsers.add(user);
            
        });
        
        return sanitizedUsers;
    }
    

    @Override
    public User findByUserName(String name) {

        return userRepository.findByEmail(name);
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);

        if (user == null) {

            throw new UsernameNotFoundException(username);
        }

        return new UserDetailsImpl(user);
    }

    @Override
    public User findById(Long id) {

        return userRepository.findOne(id);
    }

    @Override
    public User getByUuId(String uuid) {

        return userRepository.getByUuId(uuid);
    }

    @Override
    public User saveUser(User user) {

        if (userExists(user.getEmail())) {
            return null;
        }

        User newUser = new User(user.getEmail(), user.getPassword(), (HashSet<Role>) user.getUserRoles());

        return userRepository.save(newUser);
    }

    @Override
    public User saveDao(UserDao userDao) {

        if (userDao != null) {

            return userRepository.save(daoToUser.convert(userDao));
        }

        return null;
    }

    @Override
    public void deleteUser(Long id) {

        userRepository.delete(id);
    }

    @Override
    public boolean passwordAndConfirmationMatch(UserDao userDao) {

        if (userDao.getPassword().equals(userDao.getConfirmPassword())) {
            return true;
        }

        return false;
    }

    @Override
    public boolean userExists(String email) {

        if (userRepository.findByEmail(email) != null) {
            return true;
        }

        return false;
    }

    @Override
    public boolean passwordMatches(String rawPassword, String encodedPassword) {

        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword); 
    }

    @Override
    public User changePasswordTo(User user, String password) {

        user.setPassword(new BCryptPasswordEncoder().encode(password)); 
        userRepository.save(user);
        
        return user;
    }
    
    @Override
    public UserClaim createClaim(ClaimDao claimDao) {
        
        User user = userRepository.getByUuId(claimDao.getUserUuId());
        ClaimType claimType = claimTypeRepository.findByTypeName(claimDao.getUserClaimType());
        
        return claimService.updateUserClaim(user, claimType, claimDao.getUserClaimValue());
    }

    @Override
    public List<UserClaim> findUserClaims(User user) {

        List<UserClaim> claims = userClaimRepository.findByUser(user);
   
        return claims;
    }

    @Override
    public List<UserClaim> findUserClaims(User user, String type) {
        
        List<UserClaim> claims = new ArrayList<>();
        List<ClaimType> claimTypes = claimTypeRepository.findByEntityType(type);
        
        for (ClaimType claimType : claimTypes) {
            
            List<UserClaim> userClaims = userClaimRepository.findByUserAndClaimClaimType(user, claimType); 
            
            for (UserClaim userClaim : userClaims) {
                
                claims.add(userClaim);
            }
        }
        
        return claims;
    }
}
