/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.Claim;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.InformationClass;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.ProjectWbsPackage;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.repositories.ClaimRepository;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.data.repositories.ContactRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.InformationClassRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.data.repositories.ProjectWbsPackageRepository;
import org.zafritech.core.data.repositories.UserClaimRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.enums.CompanyRole;
import org.zafritech.core.enums.ProjectStatus;
import org.zafritech.core.services.ProjectService;

/**
 *
 * @author LukeS
 */
@Component
public class ProjectsInit {
    
    @Autowired
    private InformationClassRepository infoClassRepository;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
    
    @Autowired
    private ClaimRepository claimRepository;
    
    @Autowired
    private UserClaimRepository userClaimRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    private ProjectWbsPackageRepository wbsPackageRepository;
    
    @Autowired
    private ProjectService projectService;
    
    @Transactional
    public void init() {
        
        InformationClass infoClass = infoClassRepository.findByClassCode("INFO_UNCLASSIFIED");
        
        projectRepository.save(new Project(entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("PROJECT_TYPE_ENTITY", "SWD"), 
                                projectService.generateProjectNumber(entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("PROJECT_TYPE_ENTITY", "SWD")), 
                                "Zidingo RMS System", 
                                "Zidingo RMS", 
                                companyRepository.findFirstByCompanyRole(CompanyRole.PRIMARY_ORGANISATION), 
                                infoClass)
        );
        
        projectRepository.save(new Project(entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("PROJECT_TYPE_ENTITY", "CON"), 
                                projectService.generateProjectNumber(entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("PROJECT_TYPE_ENTITY", "CON")), 
                                "QF People Mover System", 
                                "Qatar PMS", companyRepository.findFirstByCompanyRole(CompanyRole.PRIMARY_ORGANISATION), 
                                infoClass)
        );
             
        projectRepository.save(new Project(entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("PROJECT_TYPE_ENTITY", "FIN"), 
                                projectService.generateProjectNumber(entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("PROJECT_TYPE_ENTITY", "FIN")), 
                                "Project Dabasir 2015", 
                                "Dabasir 2015", 
                                companyRepository.findFirstByCompanyRole(CompanyRole.PRIMARY_ORGANISATION), 
                                infoClass)
        );
        
        projectRepository.save(new Project(entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("PROJECT_TYPE_ENTITY", "ICT"), 
                                projectService.generateProjectNumber(entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("PROJECT_TYPE_ENTITY", "ICT")), 
                                "Cloud Server Project", 
                                "Cloud SVR", 
                                companyRepository.findFirstByCompanyRole(CompanyRole.PRIMARY_ORGANISATION), 
                                infoClass)
        );
        
        projectRepository.save(new Project(entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("PROJECT_TYPE_ENTITY", "PER"), 
                                projectService.generateProjectNumber(entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("PROJECT_TYPE_ENTITY", "PER")), 
                                "Professional Development", 
                                "Engineering CPD", 
                                companyRepository.findFirstByCompanyRole(CompanyRole.PRIMARY_ORGANISATION), 
                                infoClass)
        );
        
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach(projects::add);
        
        for (Project project : projects) {
            
            project.setCreatedBy(userRepository.findByEmail("admin@zidingo.org"));
            project.setProjectManager(userRepository.findByEmail("admin@zidingo.org")); 
            project.setProjectContact(contactRepository.findByEmail("admin@zidingo.org"));  
            project.setStatus(ProjectStatus.STATUS_ACTIVE); 
            projectRepository.save(project);
        }
        
        initProjectClaims();
        initProjectWbs();
    }
    
    private void initProjectClaims() {
        
        ClaimType claimType = claimTypeRepository.findFirstByTypeName("PROJECT_MANAGER");
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach(projects::add);
        
        for (Project project : projects) {
            
            Claim claim = claimRepository.save(new Claim(

                    claimType, 
                    project.getId(), 
                    claimType.getTypeDescription() + " - " + 
                    project.getProjectName() + " (" + project.getProjectNumber() + ")"));

            userClaimRepository.save(new UserClaim(project.getProjectManager(), claim));
        }
    }
    
    private void initProjectWbs() {
        
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach(projects::add);
        
        for (Project project : projects) {
            
            ProjectWbsPackage wbsPackage = new ProjectWbsPackage(project, "0101", "STL", "System Top Level");
            wbsPackageRepository.save(wbsPackage);
        }
    }
}
