/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.ValuePairDao;
import org.zafritech.core.data.domain.Claim;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserClaim;
import org.zafritech.core.data.repositories.ClaimRepository;
import org.zafritech.core.data.repositories.ClaimTypeRepository;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.data.repositories.UserClaimRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.services.ClaimService;

/**
 *
 * @author LukeS
 */
@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimTypeRepository claimTypeRepository;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private UserClaimRepository userClaimRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserClaim updateUserClaim(User user, ClaimType type, Long claimValue) {
     
        String description = type.getTypeDescription() + " - ";
        
        switch(type.getEntityType()) {

            case "PROJECT":
                  
                Project project = projectRepository.findOne(claimValue);
                description = description + project.getProjectName() + " (" + project.getProjectNumber() + ")";
            break;

            case "DOCUMENT":
                 
                Document document = documentRepository.findOne(claimValue);
                description = description + document.getDocumentName() + " (" + document.getIdentifier() + ")";
            break;

            case "CATEGORY":
                // TBD    
            break;
        }
        
        // Does the claim already exists?
        Claim claim = claimRepository.findFirstByClaimTypeAndClaimValue(type, claimValue);
        if (claim == null) { claim = claimRepository.save(new Claim(type, claimValue, description)); }

        // Does the user already have this claim?
        UserClaim userClaim = userClaimRepository.findFirstByUserAndClaim(user, claim);
        if (userClaim == null) { userClaimRepository.save(new UserClaim(user, claim)); }

        return userClaim;
    }
    
    @Override
    public UserClaim updateUserClaim(User user, ClaimType type, Long claimValue, String description) {
     
        // Does the claim already exists?
        Claim claim = claimRepository.findFirstByClaimTypeAndClaimValue(type, claimValue);
        if (claim == null) { claim = claimRepository.save(new Claim(type, claimValue, description)); }

        // Does the user already have this claim?
        UserClaim userClaim = userClaimRepository.findFirstByUserAndClaim(user, claim);
        if (userClaim == null) { userClaimRepository.save(new UserClaim(user, claim)); }

        return userClaim;
    }

    @Override
    public UserClaim updateExclusiveUserClaim(User user, ClaimType type, Long claimValue, String description) {
        
        Claim claim = claimRepository.findFirstByClaimTypeAndClaimValue(type, claimValue);
        
        if (claim != null) {
            
            UserClaim userClaim = userClaimRepository.findFirstByClaim(claim);
            
            if (userClaim != null) {
                
                userClaim.setUser(user);
                
            } else {
                
                userClaim = new UserClaim(user, claim);
            }

            return userClaimRepository.save(userClaim);
            
        } else {
            
            return updateUserClaim(user, type, claimValue, description);
        }
    }
    
    @Override
    public void removeUserClaims(String entityType, Long claimValue) {
        
        List<ClaimType> claimTypes = claimTypeRepository.findByEntityType(entityType);
        
        for(ClaimType type : claimTypes) {
            
            List<Claim> claims = claimRepository.findByClaimTypeAndClaimValue(type, claimValue);
            
            for (Claim claim : claims) {
                
                claimRepository.delete(claim); 
            }
        }
    }

    @Override
    public boolean isProjectMember(User user, Project project) {
        
        ClaimType claimType = claimTypeRepository.findByTypeName("PROJECT_MEMBER");
        Claim projectClaim = claimRepository.findFirstByClaimTypeAndClaimValue(claimType, project.getId());
        List<UserClaim> userClaims = userClaimRepository.findByClaim(projectClaim);
        
        for (UserClaim userClaim : userClaims) {
            
            if (userClaim.getUser() == user) {
                
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public List<User> findProjectMemberClaims(Project project) {
        
        List<User> members = new ArrayList<>();
        ClaimType claimType = claimTypeRepository.findByTypeName("PROJECT_MEMBER");
        Long claimValue = project.getId();
        Claim claim = claimRepository.findFirstByClaimTypeAndClaimValue(claimType, claimValue);
        
        List<UserClaim> userClaims = userClaimRepository.findByClaim(claim);
        
        for (UserClaim userClaim : userClaims) {
            
            members.add(userClaim.getUser());
        }
        
        return members;
    }

    @Override
    public List<User> findDocumentEditorClaims(Document document) {
        
        List<User> members = new ArrayList<>();
        ClaimType claimType = claimTypeRepository.findByTypeName("DOCUMENT_EDITOR");
        Long claimValue = document.getId();
        Claim claim = claimRepository.findFirstByClaimTypeAndClaimValue(claimType, claimValue);
        
        List<UserClaim> userClaims = userClaimRepository.findByClaim(claim);
        
        for (UserClaim userClaim : userClaims) {
            
            members.add(userClaim.getUser());
        }
        
        return members;
    }

    @Override
    public List<User> findDocumentReviewerClaims(Document document) {
        
        List<User> members = new ArrayList<>();
        ClaimType claimType = claimTypeRepository.findByTypeName("DOCUMENT_REVIEWER");
        Long claimValue = document.getId();
        Claim claim = claimRepository.findFirstByClaimTypeAndClaimValue(claimType, claimValue);
        
        List<UserClaim> userClaims = userClaimRepository.findByClaim(claim);
        
        for (UserClaim userClaim : userClaims) {
            
            members.add(userClaim.getUser());
        }
        
        return members;
    }

    @Override
    public List<Project> findProjectMemberships(User user) {
        
        ClaimType claimType = claimTypeRepository.findByTypeName("PROJECT_MEMBER");
        List<UserClaim> projectClaims = userClaimRepository.findByUserAndClaimClaimType(user, claimType);
        
        List<Project> projects = new ArrayList<>();
        
        for (UserClaim userClaim : projectClaims) {
            
            projects.add(projectRepository.findOne(userClaim.getClaim().getClaimValue()));
        }
        
        return projects;
    }

    @Override
    public List<User> updateDocumentEditorClaims(List<ValuePairDao> daos, Document document) {
        
        ClaimType claimType = claimTypeRepository.findByTypeName("DOCUMENT_EDITOR");
        String claimDecsription = claimType.getTypeDescription() + " - " + document.getDocumentName() + " (" + document.getDocumentName() + ")";
        Claim claim = claimRepository.findFirstByClaimTypeAndClaimValue(claimType, document.getId());
        
        List<User> users = new ArrayList<>();
        
        for (ValuePairDao dao : daos) {
            
            users.add(userRepository.findOne(dao.getId()));
        }
        
        // Delete old claims
        List<User> current = findDocumentEditorClaims(document);
        
        for (User user : current) {
          
            UserClaim userClaim = userClaimRepository.findFirstByUserAndClaim(user, claim);
            userClaimRepository.delete(userClaim); 
        }
        
        
        // Replace with new claims
        for (User user : users) {
          
            updateUserClaim(user, claimType, document.getId(), claimDecsription);
        }
        
        return findDocumentEditorClaims(document);
    }

    @Override
    public List<User> updateDocumentReviewerClaims(List<ValuePairDao> daos, Document document) {
       
        ClaimType claimType = claimTypeRepository.findByTypeName("DOCUMENT_REVIEWER");
        String claimDecsription = claimType.getTypeDescription() + " - " + document.getDocumentName() + " (" + document.getDocumentName() + ")";
        Claim claim = claimRepository.findFirstByClaimTypeAndClaimValue(claimType, document.getId());
        
        List<User> users = new ArrayList<>();
        
        for (ValuePairDao dao : daos) {
            
            users.add(userRepository.findOne(dao.getId()));
        }
        
        // Delete old claims
        List<User> current = findDocumentReviewerClaims(document);
        
        for (User user : current) {
          
            UserClaim userClaim = userClaimRepository.findFirstByUserAndClaim(user, claim);
            userClaimRepository.delete(userClaim); 
        }
        
        
        // Replace with new claims
        for (User user : users) {
          
            updateUserClaim(user, claimType, document.getId(), claimDecsription);
        }
        
        return findDocumentReviewerClaims(document);
    }
}
