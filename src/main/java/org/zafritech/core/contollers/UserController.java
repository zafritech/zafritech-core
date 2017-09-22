/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.services.UserService;

/**
 *
 * @author LukeS
 */
@Controller
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @RequestMapping(value = {"/user/profile", "/profile"})
    public String getUserProfile(Model model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        
        User user = userRepository.findByUserName(name);
            
        model.addAttribute("user", user);

        return "views/user/profile";
    }
    
    @RequestMapping(value = {"/user/dashboard", "/dashboard"})
    public String getUserDashboard(Model model) {
        
        User user = userService.loggedInUser();
        
        PageRequest request = new PageRequest(0, 5, Sort.Direction.DESC, "modifiedDate");
        
        Page<Document> documents = documentRepository.findAllByOwner(request, user);
        
        model.addAttribute("documents", documents);
        return "views/user/dashboard";
    }
    
    @RequestMapping(value = {"/user/tasks", "/tasks"})
    public String getUserTasks(Model model) {
        
        return "views/tasks/index";
    }
}
