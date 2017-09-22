/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zafritech.core.data.dao.PageNavigationDao;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.services.ClaimService;
import org.zafritech.core.services.CommonService;
import org.zafritech.core.services.UserService;

/**
 *
 * @author LukeS
 */
@Controller
@Secured({"ROLE_ADMIN"})
public class AdminUserController {
    
    @Autowired
    private CommonService commonService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ClaimService claimService;
    
    @RequestMapping(value = {"/admin/users", "/admin/users/list"})
    public String listUsers(@RequestParam(name = "s", defaultValue = "15") int pageSize,
                            @RequestParam(name = "p", defaultValue = "1") int pageNumber,
                            Model model) {
        
        PageNavigationDao navigator = commonService.getPageNavigator(userRepository.findAll().size(), pageSize, pageNumber);

        model.addAttribute("users", userService.findOrderByFirstName(pageSize, pageNumber));
        model.addAttribute("page", pageNumber);
        model.addAttribute("size", pageSize);
        model.addAttribute("userCount", navigator.getItemCount());
        model.addAttribute("list", navigator.getPageList());
        model.addAttribute("count", navigator.getPageCount());
        model.addAttribute("last", navigator.getLastPage());

        return "views/admin/users/list";
    }
    
    @RequestMapping("/admin/users/{uuid}")
    public String getUser(@PathVariable String uuid, Model model) {

        User user = userService.getByUuId(uuid);
        List<UserClaim> claims = userService.findUserClaims(user, "PROJECT");
        List<Project> projects = claimService.findProjectMemberships(user);
         
        model.addAttribute("user", userService.getByUuId(uuid));
        model.addAttribute("claims", claims); 
        model.addAttribute("projects", projects); 

        return "views/admin/users/profile";
    }
}
