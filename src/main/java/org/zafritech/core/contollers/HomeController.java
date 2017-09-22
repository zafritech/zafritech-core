/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author LukeS
 */
@Controller
public class HomeController {
    
    @RequestMapping(value={"/", "/index"})
    public String homePage(Model model) {
        
        return "views/index";
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
