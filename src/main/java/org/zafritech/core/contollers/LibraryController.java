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
public class LibraryController {
    

    @RequestMapping(value = {"/library", "/library/list"})
    public String documentsList(Model model) {
        
        model.addAttribute("titles", "titles");
        
        return "views/library/index";
    }
}
