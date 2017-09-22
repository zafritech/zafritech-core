/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.zafritech.core.data.domain.Role;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.projections.UserView;

/**
 *
 * @author LukeS
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    
    @Override
    Page<User> findAll(Pageable pageable);
   
    User findByEmail(String email);

    User findByUserName(String username);
    
    Set<User> findAllByOrderByFirstNameAsc();

    @Override
    Set<User> findAll();
    
    Set<User> findByUserRoles(Role role);

    User getByUuId(String uuid);
    
    // Projection Views
    UserView findUserViewByEmail(String email);

    UserView findUserViewByUserName(String username);
    
    Set<UserView> findUserViewByUserRoles(Role role);

    UserView getUserViewByUuId(String uuid);
}
