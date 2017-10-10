/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.core.data.dao.ProjectDao;
import org.zafritech.core.data.dao.ValuePairDao;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.InformationClass;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.projections.UserView;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.InformationClassRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.data.repositories.ProjectTypeRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.enums.ProjectStatus;
import org.zafritech.core.services.ClaimService;
import org.zafritech.core.services.ProjectService;
import org.zafritech.core.services.UserService;
import org.zafritech.core.services.UserStateService;


/**
 *
 * @author LukeS
 */
@RestController
public class ProjectRestController {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectTypeRepository projectTypeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private InformationClassRepository infoClassRepository;
    
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private ClaimService claimService;
      
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserStateService stateService;
    
    @RequestMapping(value = "/api/admin/projects/list", method = GET)
    public ResponseEntity<List<Project>> listProjects() {
  
        User user = userService.loggedInUser();
        boolean isAdmin = userService.hasRole("ROLE_ADMIN");
        List<Project> allProjects = projectRepository.findAllByOrderByProjectName();
        List<Project> projects = new ArrayList<>();
        
        for (Project project : allProjects) {
            
            if (isAdmin || claimService.isProjectMember(user, project)){
                
                projects.add(project);
            }
        }
        
        return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
    }
      
    @RequestMapping(value = "/api/projects/list/open", method = GET)
    public ResponseEntity<List<Project>> listOpenProjects() {
  
        List<Project> projects = stateService.getOpenProjects();
 
        return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
    }
      
    @RequestMapping(value = "/api/projects/list/closed", method = GET)
    public ResponseEntity<List<Project>> listClosedProjects() {
  
        User user = userService.loggedInUser();
        boolean isAdmin = userService.hasRole("ROLE_ADMIN");
        List<Project> closedProjects = new ArrayList<>();
        
        List<Project> projects = projectRepository.findAllByOrderByProjectName();
 
        for (Project project : projects) {
            
            if (!stateService.isProjectOpen(project) && (isAdmin || claimService.isProjectMember(user, project))){
                
                closedProjects.add(project);
            }
        }
 
        return new ResponseEntity<List<Project>>(closedProjects, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/projects/close/allopen", method = GET)
    public ResponseEntity<Integer> closeAllOpenProjects() {
        
        Integer numberClosed = stateService.closeAllProjects();
        
        return new ResponseEntity<Integer>(numberClosed, HttpStatus.OK);
    }
      
    @RequestMapping(value = "/api/projects/project/byid/{id}", method = GET)
    public ResponseEntity<Project> getProjectById(@PathVariable(value = "id") Long id) {
  
        Project project = projectRepository.findOne(id);
 
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }
 
    @RequestMapping(value = "/api/projects/project/new/number/{id}", method = GET)
    public ResponseEntity<String> getNewProjectNumber(@PathVariable(value = "id") Long id) {
  
        String number = projectService.generateProjectNumber(projectTypeRepository.findOne(id));
 
        return new ResponseEntity<String>(number, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/projects/project/new", method = POST)
    public ResponseEntity<Project> newProject(@RequestBody ProjectDao projectDao) {
  
        Project project = projectService.saveDao(projectDao);
 
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }
      
    @RequestMapping(value = "/api/admin/projects/update/{uuid}", method = POST)
    public ResponseEntity<Project> updateProject(@RequestBody ProjectDao projectDao,
                                                 @PathVariable(value = "uuid") String uuid) {
  
        projectDao.setId(projectRepository.getByUuId(uuid).getId());
        Project project = projectService.saveDao(projectDao);
        
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/projects/get/{uuid}", method = GET)
    public ResponseEntity<Project> getProjectProperties(@PathVariable(value = "uuid") String uuid) {
        
        Project project = projectRepository.getByUuId(uuid);
                
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/projects/status/list", method = GET)
    public List<ProjectStatus> getProjectStatusList() {
        
        return Arrays.asList(ProjectStatus.values());
    }
    
    @RequestMapping(value = "/api/admin/projects/project/members/list/{uuid}", method = GET)
    public ResponseEntity<List<UserView>> getProjectMembersList(@PathVariable(value = "uuid") String uuid) {
       
        Project project = projectRepository.getByUuId(uuid);
        List<User> users = claimService.findProjectMemberClaims(project);
         
        List<UserView> members = new ArrayList<>();
        
        for (User user : users) {
            
            members.add(userRepository.findUserViewByEmail(user.getEmail()));
        }
        
        return new ResponseEntity<List<UserView>>(members, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/projects/add/user/{uuid}", method = POST)
    public ResponseEntity<UserClaim> addProjectMembersByUuId(@RequestBody Long projectId,
                                                             @PathVariable(value = "uuid") String uuid) {
       
        User user = userRepository.getByUuId(uuid);
        ClaimType type = claimTypeRepository.findFirstByTypeName("PROJECT_MEMBER");
        
        UserClaim claim = claimService.updateUserClaim(user, type, projectId);
        
        return new ResponseEntity<UserClaim>(claim, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/projects/members/add/{uuid}", method = POST)
    public ResponseEntity<List<User>> getProjectMembersAdd(@RequestBody List<ValuePairDao> daos,
                                                           @PathVariable(value = "uuid") String uuid) {
       
        List<User> users = new ArrayList<>();
        Project project = projectRepository.getByUuId(uuid);
        
        for (ValuePairDao dao : daos) {
            
            users.add(userRepository.findOne(dao.getId()));
        }
        
        List<User> members = projectService.addProjectMembers(project, users);
        
        return new ResponseEntity<List<User>>(members, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/project/infoclass/list", method = GET)
    public ResponseEntity<List<InformationClass>> listInformationClasses() {
        
        List<InformationClass> infoClasses = infoClassRepository.findAllByOrderByClassNameAsc(); 
        
        return new ResponseEntity<List<InformationClass>>(infoClasses, HttpStatus.OK);
    }
}
