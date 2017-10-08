/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.FolderTypeRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.services.ClaimService;
import org.zafritech.core.services.UserService;
import org.zafritech.core.services.UserStateService;

/**
 *
 * @author LukeS
 */
@Controller
public class ProjectController {
    
    @Autowired
    private FolderRepository folderRepository;
    
    @Autowired
    private FolderTypeRepository folderTypeRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserService userService;
    
    
    @Autowired
    private ClaimService claimService;
    
    @Autowired
    private UserStateService stateService;
    
    @RequestMapping(value = {"/projects", "/projects/list"})
    public String getProjectsList(Model model) {
        
        User user = userService.loggedInUser();
        boolean isAdmin = userService.hasRole("ROLE_ADMIN");
        List<Project> allProjects = projectRepository.findAllByOrderByProjectName();
        
        List<Project> projects = new ArrayList<>();
        
        for (Project project : allProjects) {
            
            if (isAdmin || claimService.isProjectMember(user, project)){
                
                projects.add(project);
            }
        }
        
        model.addAttribute("projects", projects);
        
        return "views/project/index";
    }
    
    @RequestMapping("/projects/{uuid}")
    public String getProject(@PathVariable String uuid, Model model) {
        
        Project project = projectRepository.getByUuId(uuid);
        Folder folder = folderRepository.findFirstByProjectAndFolderType(project, folderTypeRepository.findByTypeKey("FOLDER_PROJECT"));
        List<User> members = claimService.findProjectMemberClaims(project);
        
        model.addAttribute("project", project);
        model.addAttribute("folder", folder);   
        model.addAttribute("members", members);
        
        stateService.updateOpenProject(project); 
        
        return "views/project/project";
    }
    
    @RequestMapping("/projects/close/{uuid}")
    public String closeProject(@PathVariable String uuid, Model model) {
        
        Project project = projectRepository.getByUuId(uuid);
        stateService.updateCloseProject(project);
        
        return "redirect:/projects";
    }
}
