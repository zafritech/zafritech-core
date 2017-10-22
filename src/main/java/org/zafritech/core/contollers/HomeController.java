/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.services.UserSessionService;

/**
 *
 * @author LukeS
 */
@Controller
public class HomeController {
    
    @Autowired
    private UserSessionService userSessionService;
    
    @RequestMapping(value={"/", "/index"})
    public String homePage(Model model) {
         
        Project project = userSessionService.getLastOpenProject();
        
        if (project != null) {
            
            return "redirect:/projects/" + project.getUuId(); 
            
        } else {
        
            return "views/index";
        }
    }
    
    @RequestMapping("/docs/references/help")
    public String documentOnlineHelp(Model model) {
        
        return "views/docs/help";
    }
    
    @RequestMapping("/docs/references/links")
    public String documentReferenceLinks(Model model) {
        
        return "views/docs/links";
    }
}
