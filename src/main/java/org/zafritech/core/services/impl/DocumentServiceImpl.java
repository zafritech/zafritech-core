/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.DocDao;
import org.zafritech.core.data.dao.DocEditDao;
import org.zafritech.core.data.domain.Document;
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
import org.zafritech.core.enums.DocumentStatus;
import org.zafritech.core.services.ClaimService;
import org.zafritech.core.services.DocumentService;
import org.zafritech.core.services.UserService;

/**
 *
 * @author LukeS
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private EntityTypeRepository entitytTypeRepository;
    
    @Autowired
    private DocumentContentDescriptorRepository descriptorRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectWbsPackageRepository wbsPackageRepository;
    
    @Autowired
    private FolderRepository folderRepository;
     
    @Autowired
    private InformationClassRepository infoClassRepository;
    
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ClaimService claimService;
    
    @Override
    public Document saveDao(DocDao docDao) {
        
        User owner = userService.loggedInUser();
        
        Document document = documentRepository.save(new Document(
        
                docDao.getIdentifier(),
                docDao.getDocumentName(),
                entitytTypeRepository.findOne(docDao.getTypeId()),
                descriptorRepository.findOne(docDao.getDecriptorId()),
                projectRepository.findOne(docDao.getProjectId()),
                wbsPackageRepository.findOne(docDao.getWbsId()),
                folderRepository.findOne(docDao.getFolderId()),
                infoClassRepository.findOne(docDao.getInfoClassId()),
                "0A"
        ));
        
        document.setDocumentLongName(docDao.getDocumentLongName());
        document.setDocumentDescription(docDao.getDocumentDescription());
        document.setOwner(owner);
        document.setDocumentIssue("0A");
        document = documentRepository.save(document);
        
        claimService.updateUserClaim(owner, claimTypeRepository.findByTypeName("DOCUMENT_OWNER"), document.getId());
        
        return document;
    }

    @Override
    public Document duplicate(Long id) {
        
        User owner = userService.loggedInUser();
        Document oldDoc = documentRepository.findOne(id);
        
        Document document = new Document(
        
                oldDoc.getIdentifier() + "-COPY",
                oldDoc.getDocumentName() + " - Copy",
                oldDoc.getDocumentType(),
                oldDoc.getContentDescriptor(),
                oldDoc.getProject(),
                oldDoc.getWbs(),
                oldDoc.getFolder(),
                oldDoc.getInfoClass(),
                "0A"
        );
        
        document.setDocumentIssue(oldDoc.getDocumentIssue()); 
        document.setOwner(owner); 
        document = documentRepository.save(document);
        
        claimService.updateUserClaim(owner, claimTypeRepository.findByTypeName("DOCUMENT_OWNER"), document.getId());
        
        return document;
    }

    @Override
    public Document saveEditDao(DocEditDao docEditDao) {
        
        User loginUser = userService.loggedInUser();
        User owner = (docEditDao.getOwnerId() != null) ? userRepository.findOne(docEditDao.getOwnerId()) : loginUser;
        
        Document document = documentRepository.findOne(docEditDao.getId());
        
        document.setIdentifier(docEditDao.getIdentifier());
        document.setDocumentName(docEditDao.getDocumentName());
        document.setDocumentLongName(docEditDao.getDocumentLongName());
        document.setDocumentDescription(docEditDao.getDocumentDescription());
        document.setDocumentIssue(docEditDao.getDocumentIssue()); 
        document.setStatus(DocumentStatus.valueOf(docEditDao.getStatus()));
        
        document.setProject(projectRepository.findOne(docEditDao.getProjectId()));
        document.setFolder(folderRepository.findOne(docEditDao.getFolderId()));
        document.setDocumentType(entitytTypeRepository.findOne(docEditDao.getTypeId()));
        document.setInfoClass(infoClassRepository.findOne(docEditDao.getInfoClassId()));
        document.setOwner(owner); 
        document = documentRepository.save(document);
        
        claimService.updateUserClaim(owner, claimTypeRepository.findByTypeName("DOCUMENT_OWNER"), document.getId());
        
        return document; 
    }
}
