/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.Claim;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.InformationClass;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.repositories.ClaimRepository;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.data.repositories.ContactRepository;
import org.zafritech.core.data.repositories.InformationClassRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.data.repositories.ProjectTypeRepository;
import org.zafritech.core.data.repositories.UserClaimRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.enums.CompanyRole;
import org.zafritech.core.enums.ProjectStatus;

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
    private ProjectTypeRepository projectTypeRepository;
    
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
    
    @Transactional
    public void init() {
        
        InformationClass infoClass = infoClassRepository.findByClassCode("INFO_UNCLASSIFIED");
                    
        projectRepository.save(new HashSet<Project>() {{
                
                add(new Project(projectTypeRepository.findByTypeCode("SWD"), "1610SWD-WA201", "Zidingo RMS System", "Zidingo RMS", companyRepository.findFirstByCompanyRole(CompanyRole.PRIMARY_ORGANISATION), infoClass)); 
                add(new Project(projectTypeRepository.findByTypeCode("CON"), "1202CON-RL502", "QF People Mover System", "Qatar PMS", companyRepository.findFirstByCompanyRole(CompanyRole.PRIMARY_ORGANISATION), infoClass)); 
                add(new Project(projectTypeRepository.findByTypeCode("FIN"), "1507FIN-GN101", "Project Dabasir 2015", "Dabasir 2015", companyRepository.findFirstByCompanyRole(CompanyRole.PRIMARY_ORGANISATION), infoClass)); 
                add(new Project(projectTypeRepository.findByTypeCode("ICT"), "1602ICT-CL301", "Cloud Server Project", "Cloud SVR", companyRepository.findFirstByCompanyRole(CompanyRole.PRIMARY_ORGANISATION), infoClass)); 
                add(new Project(projectTypeRepository.findByTypeCode("PER"), "9401PER-GN101", "Professional Development", "Engineering CPD", companyRepository.findFirstByCompanyRole(CompanyRole.PRIMARY_ORGANISATION), infoClass)); 
            }
        });
        
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
}
