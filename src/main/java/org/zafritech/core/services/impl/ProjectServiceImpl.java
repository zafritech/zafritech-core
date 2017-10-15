/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.ProjectDao;
import org.zafritech.core.data.domain.Claim;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.domain.TemplateVariable;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.DocumentContentDescriptor;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.InformationClass;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.ProjectWbsPackage;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.repositories.ClaimRepository;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.data.repositories.ContactRepository;
import org.zafritech.core.data.repositories.DocumentContentDescriptorRepository;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.InformationClassRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.data.repositories.ProjectWbsPackageRepository;
import org.zafritech.core.data.repositories.UserClaimRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.enums.TemplateVariableCategories;
import org.zafritech.core.enums.ProjectStatus;
import org.zafritech.core.services.ClaimService;
import org.zafritech.core.services.ProjectService;
import org.zafritech.core.services.UserService;
import org.zafritech.core.data.repositories.TemplateVariableRepository;
import org.zafritech.core.services.DocumentService;

/**
 *
 * @author LukeS
 */
@Service
public class ProjectServiceImpl implements ProjectService {
 
    @Autowired
    private CompanyRepository companyRepository;
   
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectWbsPackageRepository wbsPackageRepository;
    
    @Autowired
    private InformationClassRepository infoClassRepository;

    @Autowired
    private EntityTypeRepository entityTypeRepository;

    @Autowired
    private FolderRepository folderRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private DocumentContentDescriptorRepository descriptorRepository;
    
    @Autowired
    private ContactRepository contactRepository;
 
    @Autowired
    private ClaimRepository claimRepository;
 
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
 
    @Autowired
    private UserClaimRepository userClaimRepository;
 
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ClaimService claimService;
    
    @Autowired
    private TemplateVariableRepository configRepository;
    
    @Autowired
    private DocumentService documentService;
    
    @Override
    public Project saveDao(ProjectDao dao) {
        
        User manager;
        
        Company company = companyRepository.findOne(dao.getCompanyId());
        Project project;
        
        if (dao.getId() != null) {
        
            project = projectRepository.findOne(dao.getId());
            project.setProjectName(dao.getProjectName());
            project.setProjectShortName(dao.getProjectShortName());
            project.setProjectSponsor(company); 
            project.setModifiedDate(new Timestamp(System.currentTimeMillis())); 
            if (dao.getProjectNumber() != null) { project.setProjectNumber(dao.getProjectNumber()); }
            
        } else {
            
            project = new Project(entityTypeRepository.findOne(dao.getProjectTypeId()),
                                  dao.getProjectNumber(),
                                  dao.getProjectName(), 
                                  dao.getProjectShortName(), 
                                  company,
                                  infoClassRepository.findOne(dao.getInfoClassId()));
        }

        manager = (dao.getManagerId() != null) ? userRepository.findOne(dao.getManagerId()) : userService.loggedInUser();
       
        if (dao.getInfoClassId() != null) { project.setInfoClass(infoClassRepository.findOne(dao.getInfoClassId())); }
        if (dao.getProjectTypeId() != null) { project.setProjectType(entityTypeRepository.findOne(dao.getProjectTypeId())); }
        if (dao.getStartDate() != null) { project.setStartDate(Date.valueOf(dao.getStartDate())); }
        if (dao.getEndDate() != null) { project.setEndDate(Date.valueOf(dao.getEndDate())); }
        if (dao.getStatus() != null) { project.setStatus(ProjectStatus.valueOf(dao.getStatus())); }
        if (dao.getContactId() != null) { project.setProjectContact(contactRepository.findOne(dao.getContactId())); }
        if (dao.getProjectDescription() != null) { project.setProjectDescription(dao.getProjectDescription()); }
        
        project.setProjectManager(manager);
        project.setCreatedBy(userService.loggedInUser()); 

        project = projectRepository.save(project);
        
        // For new project
        if (dao.getId() == null) {

            // Create initial top level WBS packages
            wbsPackageRepository.save(new ProjectWbsPackage(project, "0101", "SLC", "System Level Concept"));
            wbsPackageRepository.save(new ProjectWbsPackage(project, "0102", "SLP", "System Level Planning"));
            wbsPackageRepository.save(new ProjectWbsPackage(project, "0103", "SLS", "System Level Specification"));
            wbsPackageRepository.save(new ProjectWbsPackage(project, "0104", "SLD", "System Level Design"));
            wbsPackageRepository.save(new ProjectWbsPackage(project, "0105", "SLI", "System Level Integration"));
            wbsPackageRepository.save(new ProjectWbsPackage(project, "0106", "SLV", "System Level Verification and Validation"));
            
            // Create initial folders
            Folder folder = folderRepository.save(new Folder(project.getProjectShortName(), entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_PROJECT"), null, project, 0));
            folderRepository.save(new Folder("Concept", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 0));
            folderRepository.save(new Folder("Planning", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 1));
            folderRepository.save(new Folder("Specification", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 2));
            folderRepository.save(new Folder("Design", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 3));
            folderRepository.save(new Folder("Integration", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 4));
            folderRepository.save(new Folder("Validation", entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_DOCUMENT"), folder, project, 5));

            // Create project manager claim
            ClaimType claimType = claimTypeRepository.findFirstByTypeName("PROJECT_MANAGER");
            String description = claimType.getTypeDescription() + " - " + project.getProjectName() + " (" + project.getProjectNumber() + ")";
            claimService.updateUserClaim(manager, claimType, project.getId(), description);

            // Create project member claim
            claimType = claimTypeRepository.findFirstByTypeName("PROJECT_MEMBER");
            description = claimType.getTypeDescription() + " - " + project.getProjectName() + " (" + project.getProjectNumber() + ")";
            claimService.updateUserClaim(manager, claimType, project.getId(), description);
        
            documentService.initialiseNewProjectDocuments(project, userService.loggedInUser());
        }
        
        return project;
    }

    @Override
    public User addMemberToProject(Project project, User user) {
        
        ClaimType claimType = claimTypeRepository.findByTypeName("PROJECT_MEMBER");
        String claimDecsription = claimType.getTypeDescription() + " - " + project.getProjectName() + " (" + project.getProjectNumber() + ")";
        
        UserClaim claim = claimService.updateUserClaim(user, claimType, project.getId(), claimDecsription);
        
        return claim.getUser();
    }

    @Override
    public List<User> addProjectMembers(Project project, List<User> users) {
        
        ClaimType claimType = claimTypeRepository.findByTypeName("PROJECT_MEMBER");
        Claim claim = claimRepository.findFirstByClaimTypeAndClaimValue(claimType, project.getId());
        String claimDecsription = claimType.getTypeDescription() + " - " + project.getProjectName() + " (" + project.getProjectNumber() + ")";
        
        // Delete old claims
        List<User> current = claimService.findProjectMemberClaims(project);
        
        for (User user : current) {
          
            UserClaim userClaim = userClaimRepository.findFirstByUserAndClaim(user, claim);
            userClaimRepository.delete(userClaim); 
        }
        
        // Replace with new claims
        for (User user : users) {
          
            claimService.updateUserClaim(user, claimType, project.getId(), claimDecsription);
        }
        
        return claimService.findProjectMemberClaims(project); 
    }

    @Override
    public String generateProjectNumber(EntityType type) {
        
        Map<String, String> valuesMap = new HashMap<String, String>();
        
        List<TemplateVariable> projectProperties = configRepository.findByCategory(TemplateVariableCategories.VARIABLES_PROJECT);
        
        for (TemplateVariable config : projectProperties) {
            
            valuesMap.put(config.getVariable(), config.getValue());
        }
        
        StrSubstitutor subst = new StrSubstitutor(valuesMap);
        
        String numericValue;
        String format = String.format("%%0%dd", Integer.valueOf(subst.replace("${project_numbering_digits}")));
        
        Project project = projectRepository.findFirstByOrderByNumericNumberDesc();
        
        if (project != null) {
            
            if (project.getNumericNumber() != null) {
            
                numericValue = String.format(format, Integer.valueOf(project.getNumericNumber()) + 1);
                
            } else {
                
                numericValue = String.format(format, Integer.valueOf(project.getProjectNumber().replaceAll("[^0-9]", "")) + 1);
            }
            
        } else {
            
            numericValue = String.format(format, Integer.valueOf(subst.replace("${project_numbering_start}")));
        }
        
        valuesMap.put("project_type_code", type.getEntityTypeCode());
        valuesMap.put("project_numbering_numberic", numericValue);
        
        return subst.replace("${project_numbering_format}"); 
    }
}
