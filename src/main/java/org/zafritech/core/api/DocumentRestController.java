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
import org.zafritech.core.data.dao.DocDao;
import org.zafritech.core.data.dao.DocEditDao;
import org.zafritech.core.data.dao.ValuePairDao;
import org.zafritech.core.data.domain.Claim;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.DocumentType;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.projections.UserView;
import org.zafritech.core.data.repositories.ClaimRepository;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.DocumentTypeRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.data.repositories.UserClaimRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.enums.DocumentStatus;
import org.zafritech.core.services.ClaimService;
import org.zafritech.core.services.DocumentService;

/**
 *
 * @author LukeS
 */
@RestController
public class DocumentRestController {
   
    @Autowired
    private DocumentService documentService;
   
    @Autowired
    private DocumentTypeRepository documentTypeRepository;
   
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ClaimService claimService;
    
    @Autowired
    private ClaimTypeRepository claimTypeRepository;
    
    @Autowired
    private UserClaimRepository userClaimRepository;
    
    @Autowired
    private ClaimRepository claimRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @RequestMapping(value = "/api/documents/document/create/new", method = POST)
    public ResponseEntity<Document> newDocument(@RequestBody DocDao docDao) {
  
        Document document = documentService.saveDao(docDao);
 
        return new ResponseEntity<Document>(document, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/document/update", method = POST)
    public ResponseEntity<Document> updateDocument(@RequestBody DocEditDao docEditDao) {
  
        Document document = documentService.saveEditDao(docEditDao);
 
        return new ResponseEntity<Document>(document, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/document/duplicate/subtree/{id}", method = GET)
    public ResponseEntity<Document> duplicateFolderSubtree(@PathVariable(value = "id") Long id) {
        
        Document document = documentService.duplicate(id); 

        return new ResponseEntity<Document>(document, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/document/{id}", method = GET)
    public ResponseEntity<Document> getDocument(@PathVariable(value = "id") Long id) {
        
        Document document = documentRepository.findOne(id); 

        return new ResponseEntity<Document>(document, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/document/getproject/{id}", method = GET)
    public ResponseEntity<Project> getProjectFromDocument(@PathVariable(value = "id") Long id) {
        
        Document document = documentRepository.findOne(id); 

        return new ResponseEntity<Project>(document.getProject(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/byproject/{id}", method = GET)
    public ResponseEntity<List<Document>> getProjectsDocuments(@PathVariable(value = "id") Long id) {
        
        List<Document> documents = documentRepository.findByProjectOrderByDocumentNameAsc(projectRepository.findOne(id));  
        
        return new ResponseEntity<List<Document>>(documents, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/documents/rename/{id}", method = POST)
    public ResponseEntity<Document> documentRename(@RequestBody ValuePairDao vpDao,
                                                   @PathVariable(value = "id") Long id) {
        
        Document document = documentRepository.findOne(id); 
        document.setDocumentName(vpDao.getItemName()); 
        document = documentRepository.save(document);
        
        return new ResponseEntity<Document>(document, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/types/list", method = GET)
    public ResponseEntity<List<DocumentType>> listDocumentTypes() {
        
        List<DocumentType> docTypes = documentTypeRepository.findByOrderByDocumentTypeName(); 
        
        return new ResponseEntity<List<DocumentType>>(docTypes, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/types/byid/{id}", method = GET)
    public ResponseEntity<DocumentType> listDocumentTypeItem(@PathVariable Long id) {
        
        DocumentType docType = documentTypeRepository.findOne(id); 
        
        return new ResponseEntity<DocumentType>(docType, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/status/list", method = GET)
    public List<DocumentStatus> getDocumentStatusList() {
        
        return Arrays.asList(DocumentStatus.values());
    }
    
    @RequestMapping(value = "/api/documents/document/delete/{id}", method = GET)
    public ResponseEntity<Integer> deleteDocument(@PathVariable Long id) {
        
        /************************************************************************
        * TBD: Check if the document can be deleted. Waiting for business logic
        *************************************************************************/
        
        // Remove claims that would be orphaned
        claimService.removeUserClaims("DOCUMENT", id);
        documentRepository.delete(id);
        
        return new ResponseEntity<Integer> (1, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/projects/project/members/list/{uuid}", method = GET)
    public ResponseEntity<List<UserView>> getProjectMembersList(@PathVariable(value = "uuid") String uuid) {
       
        Project project = projectRepository.getByUuId(uuid);
        List<User> users = claimService.findProjectMemberClaims(project);
        
        List<UserView> members = new ArrayList<>();
        
        for (User user : users) {
            
            members.add(userRepository.findUserViewByEmail(user.getEmail()));
        }
        
        return new ResponseEntity<List<UserView>>(members, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/document/editors/list/{uuid}", method = GET)
    public ResponseEntity<List<UserView>> getDocumentEditorsList(@PathVariable(value = "uuid") String uuid) {
       
        Document document = documentRepository.findByUuId(uuid);
        List<User> users = claimService.findDocumentEditorClaims(document);
         
        List<UserView> members = new ArrayList<>();
        
        for (User user : users) {
            
            members.add(userRepository.findUserViewByEmail(user.getEmail()));
        }
        
        return new ResponseEntity<List<UserView>>(members, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/document/reviewers/list/{uuid}", method = GET)
    public ResponseEntity<List<UserView>> getDocumentReviewersList(@PathVariable(value = "uuid") String uuid) {
       
        Document document = documentRepository.findByUuId(uuid);
        List<User> users = claimService.findDocumentReviewerClaims(document);
         
        List<UserView> members = new ArrayList<>();
        
        for (User user : users) {
            
            members.add(userRepository.findUserViewByEmail(user.getEmail()));
        }
        
        return new ResponseEntity<List<UserView>>(members, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/document/distribution/list/{uuid}", method = GET)
    public ResponseEntity<List<UserView>> getDocumentDistributionList(@PathVariable(value = "uuid") String uuid) {
       
        Document document = documentRepository.findByUuId(uuid);
        List<User> users = document.getDistributions();
         
        List<UserView> members = new ArrayList<>();
        
        for (User user : users) {
            
            members.add(userRepository.findUserViewByEmail(user.getEmail()));
        }
        
        return new ResponseEntity<List<UserView>>(members, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/document/ditribution/list/update/{uuid}", method = POST)
    public ResponseEntity<List<UserView>> updateDocumentDistributionList(@RequestBody List<ValuePairDao> daos,
                                                                         @PathVariable(value = "uuid") String uuid) {
        List<User> users = new ArrayList<>();
        
        for (ValuePairDao dao : daos) {
            
            users.add(userRepository.findOne(dao.getId()));
        }
       
        Document document = documentRepository.findByUuId(uuid);
        document.setDistributions(users);
        documentRepository.save(document);
         
        List<UserView> recepients = new ArrayList<>();
        
        for (User user : users) {
            
            recepients.add(userRepository.findUserViewByEmail(user.getEmail()));
        }
        
        return new ResponseEntity<List<UserView>>(recepients, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/document/owner/get/{uuid}", method = GET)
    public ResponseEntity<UserView> getDocumentOwner(@PathVariable(value = "uuid") String uuid) {
       
        Document document = documentRepository.findByUuId(uuid);
        ClaimType claimType = claimTypeRepository.findByTypeName("DOCUMENT_OWNER");
        Claim claim = claimRepository.findFirstByClaimTypeAndClaimValue(claimType, document.getId());
        UserClaim userClaim = userClaimRepository.findFirstByClaim(claim);
        
        if (userClaim == null) {
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        
        return new ResponseEntity<UserView>(userRepository.findUserViewByEmail(userClaim.getUser().getEmail()), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/document/approver/get/{uuid}", method = GET)
    public ResponseEntity<UserView> getDocumentApprover(@PathVariable(value = "uuid") String uuid) {
       
        Document document = documentRepository.findByUuId(uuid);
        ClaimType claimType = claimTypeRepository.findByTypeName("DOCUMENT_APPROVER");
        Claim claim = claimRepository.findFirstByClaimTypeAndClaimValue(claimType, document.getId());
        UserClaim userClaim = userClaimRepository.findFirstByClaim(claim);
        
        if (userClaim == null) {
            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        
        return new ResponseEntity<UserView>(userRepository.findUserViewByEmail(userClaim.getUser().getEmail()), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/document/editors/add/{uuid}", method = POST)
    public ResponseEntity<List<UserView>> updateDocumentEditorsList(@RequestBody List<ValuePairDao> daos,
                                                                    @PathVariable(value = "uuid") String uuid) {
       
        Document document = documentRepository.findByUuId(uuid);
        List<User> users = claimService.updateDocumentEditorClaims(daos, document);
        
        List<UserView> editors = new ArrayList<>();
        
        for (User user : users) {
            
            editors.add(userRepository.findUserViewByEmail(user.getEmail()));
        }
         
        return new ResponseEntity<List<UserView>>(editors, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/document/reviewers/add/{uuid}", method = POST)
    public ResponseEntity<List<UserView>> updateDocumentReviewersList(@RequestBody List<ValuePairDao> daos,
                                                               @PathVariable(value = "uuid") String uuid) {
       
        Document document = documentRepository.findByUuId(uuid);
        List<User> users = claimService.updateDocumentReviewerClaims(daos, document);
         
        List<UserView> reviewers = new ArrayList<>();
        
        for (User user : users) {
            
            reviewers.add(userRepository.findUserViewByEmail(user.getEmail()));
        }
         
        return new ResponseEntity<List<UserView>>(reviewers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/document/owner/add/{uuid}", method = POST)
    public ResponseEntity<UserClaim> updateDocumentAuthor(@RequestBody Long userId,
                                                          @PathVariable(value = "uuid") String uuid) {
       
        // Update document properties
        User author = userRepository.findOne(userId); 
        Document document = documentRepository.findByUuId(uuid);
        document.setOwner(author);
        document = documentRepository.save(document);
        
        ClaimType claimType = claimTypeRepository.findByTypeName("DOCUMENT_OWNER");
        String decsription = claimType.getTypeDescription() + " - " + document.getDocumentName() + " (" + document.getDocumentName() + ")";
        
        UserClaim userClaim = claimService.updateExclusiveUserClaim(author, claimType, document.getId(), decsription);
        
         
        return new ResponseEntity<UserClaim>(userClaim, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/documents/document/approver/add/{uuid}", method = POST)
    public ResponseEntity<UserClaim> updateDocumentApprover(@RequestBody Long userId,
                                                            @PathVariable(value = "uuid") String uuid) {
       
        Document document = documentRepository.findByUuId(uuid);
        User approver = userRepository.findOne(userId); 
        ClaimType claimType = claimTypeRepository.findByTypeName("DOCUMENT_APPROVER");
        String decsription = claimType.getTypeDescription() + " - " + document.getDocumentName() + " (" + document.getDocumentName() + ")";
        
        UserClaim userClaim = claimService.updateExclusiveUserClaim(approver, claimType, document.getId(), decsription);
         
        return new ResponseEntity<UserClaim>(userClaim, HttpStatus.OK);
    }
}
