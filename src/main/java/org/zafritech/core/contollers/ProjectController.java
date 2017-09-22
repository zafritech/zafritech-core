/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.repositories.ProjectRepository;

/**
 *
 * @author LukeS
 */
@Controller
public class ProjectController {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @RequestMapping(value = {"/projects", "/projects/list"})
    public String getProjectsList(Model model) {
        
        List<Project> projects = projectRepository.findAllByOrderByProjectName();
        
        model.addAttribute("projects", projects);
        
        return "views/project/index";
    }
    
    @RequestMapping("/projects/{uuid}")
    public String getProject(@PathVariable String uuid, Model model) {
        
        Project project = projectRepository.getByUuId(uuid);
        
        model.addAttribute("project", project);
        
        return "views/project/project";
    }
}
