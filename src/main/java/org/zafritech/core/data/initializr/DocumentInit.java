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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.DocumentContentDescriptorRepository;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.InformationClassRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.data.repositories.ProjectWbsPackageRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.services.DocumentService;

/**
 *
 * @author LukeS
 */
@Component
public class DocumentInit {
    
    @Value("${zafritech.organisation.domain}")
    private String domain;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
   
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DocumentService documentService;
    
    @Transactional
    public void init() {
        
        User owner = userRepository.findByEmail("admin@" + domain);
        
        List<Project> projects = projectRepository.findAllByOrderByProjectName();
        List<ClaimType> claimTypes = new ArrayList<>();
        claimTypeRepository.findByEntityType("DOCUMENT").forEach(claimTypes::add);
        
        for (Project project : projects) {
            
            documentService.initNewProjectDocuments(project, owner);
        }
        
        initDocumentOwner(owner);
    }
    
    private void initDocumentOwner(User user) {
        
        List<Document> documents = new ArrayList<>();
        documentRepository.findAll().forEach(documents::add);
        
        for (Document document : documents) {
            
            document.setOwner(user);
            documentRepository.save(document);
        }
    }
}
