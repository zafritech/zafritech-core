/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.Claim;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.DocumentType;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.InformationClass;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.repositories.ClaimRepository;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.DocumentTypeRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.InformationClassRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.data.repositories.UserClaimRepository;
import org.zafritech.core.data.repositories.UserRepository;

/**
 *
 * @author LukeS
 */
@Component
public class DocumentInit {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private FolderRepository folderRepository;
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @Autowired
    private DocumentTypeRepository documentTypeRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private InformationClassRepository infoClassRepository;
    
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
   
    @Autowired
    private ClaimRepository claimRepository;
     
    @Autowired
    private UserClaimRepository userClaimRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public void init() {
        
        List<Project> projects = projectRepository.findAllByOrderByProjectName();
        List<ClaimType> claimTypes = new ArrayList<>();
        claimTypeRepository.findByEntityType("DOCUMENT").forEach(claimTypes::add);
        String documentIssue = "0A";
        
        for (Project project : projects) {
            
            Folder root = folderRepository.findFirstByProjectAndFolderType(project, entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_PROJECT"));
            List<Folder> folders = folderRepository.findByParent(root);

            int counter = Integer.valueOf( new SimpleDateFormat("yy").format(new Date()) + "01");
            
            String identRoot = project.getProjectCode() + "-" + project.getProjectSponsor().getCompanyCode();
            InformationClass infoClass = infoClassRepository.findByClassCode("INFO_OFFICIAL");
                    
            for (Folder folder : folders) {
                
                DocumentType docType = documentTypeRepository.findByTypeCode("RLS");
                String ident = identRoot + '-' + docType.getTypeCode().toUpperCase() + "-" + String.format("%03d", counter);
                String name = folder.getFolderName() + ' ' + docType.getTypeCode().toUpperCase() + ' ' + String.format("%03d", counter++);
                documentRepository.save(new Document(ident, name, docType, project, folder, infoClass, documentIssue));
                                
                docType = documentTypeRepository.findByTypeCode("SYS");
                ident = identRoot + '-' + docType.getTypeCode().toUpperCase() + "-" + String.format("%03d", counter);
                name = folder.getFolderName() + ' ' + docType.getTypeCode().toUpperCase() + ' ' + String.format("%03d", counter++);
                documentRepository.save(new Document(ident, name, docType, project, folder, infoClass, documentIssue));
                
                docType = documentTypeRepository.findByTypeCode("SRS");
                ident = identRoot + '-' + docType.getTypeCode().toUpperCase() + "-" + String.format("%03d", counter);
                name = folder.getFolderName() + ' ' + docType.getTypeCode().toUpperCase() + ' ' + String.format("%03d", counter++);
                documentRepository.save(new Document(ident, name, docType, project, folder, infoClass, documentIssue));
                
                docType = documentTypeRepository.findByTypeCode("ICD");
                ident = identRoot + '-' + docType.getTypeCode().toUpperCase() + "-" + String.format("%03d", counter);
                name = folder.getFolderName() + ' ' + docType.getTypeCode().toUpperCase() + ' ' + String.format("%03d", counter++);
                documentRepository.save(new Document(ident, name, docType, project, folder, infoClass, documentIssue));
                
            }
            
            initClaims(project);
        }
        
        initDocumentOwner();
    }
    
    private void initDocumentOwner() {
        
        User owner = userRepository.findByEmail("admin@zidingo.org");
        List<Document> documents = new ArrayList<>();
        documentRepository.findAll().forEach(documents::add);
        
        for (Document document : documents) {
            
            document.setOwner(owner);
            documentRepository.save(document);
        }
    }
    
    private void initClaims(Project project) {
        
        ClaimType claimType = claimTypeRepository.findFirstByTypeName("DOCUMENT_OWNER");

        List<Document> documents = new ArrayList<>();
        documentRepository.findByProject(project).forEach(documents::add);
 
        for (Document document : documents) {

            Claim claim = claimRepository.save(new Claim(

                    claimType, 
                    document.getId(), 
                    claimType.getTypeDescription() + " - " + 
                    document.getDocumentName() + " (" + document.getIdentifier() + ")"));

            userClaimRepository.save(new UserClaim(project.getProjectManager(), claim));
        }
    }
}
