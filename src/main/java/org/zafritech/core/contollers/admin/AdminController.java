/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author LukeS
 */
@Controller
public class AdminController {
    
    @RequestMapping("/admin")
    public String adminHomePage(Model model) {
        
        return "views/admin/index";
    }
     
    @RequestMapping("/admin/tasks")
    public String adminTasks(Model model) {
        
        return "views/admin/tasks/index";
    }
     
    @RequestMapping("/admin/reports")
    public String adminReports(Model model) {
        
        return "views/admin/reporting/index";
    }
    
    @RequestMapping("/admin/system/monitor")
    public String adminSystemMonitor(Model model) {
        
        return "views/admin/system/monitor";
    }
       
    @RequestMapping("/admin/messages")
    public String adminMessages(Model model) {
        
        return "views/admin/messages/messages";
    }
    
    @RequestMapping("/admin/messages/template/editor")
    public String createMessageTemplate(Model model) {
        
        return "views/admin/messages/template";
    }
}
