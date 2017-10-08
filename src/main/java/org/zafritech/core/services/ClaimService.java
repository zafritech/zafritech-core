/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import java.util.List;
import org.zafritech.core.data.dao.ValuePairDao;
import org.zafritech.core.data.domain.ClaimType;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserClaim;

/**
 *
 * @author LukeS
 */
public interface ClaimService {
   
    UserClaim updateUserClaim(User user, ClaimType type, Long claimValue);
   
    UserClaim updateUserClaim(User user, ClaimType type, Long claimValue, String description);
   
    UserClaim updateExclusiveUserClaim(User user, ClaimType type, Long claimValue, String description);
    
    void removeUserClaims(String entityType, Long claimValue);
    
    boolean isProjectMember(User user, Project project);
    
    List<User> findProjectMemberClaims(Project project);
    
    List<Project> findProjectMemberships(User user);
    
    List<User> findDocumentEditorClaims(Document document);
    
    List<User> findDocumentReviewerClaims(Document document);
    
    List<User> updateDocumentEditorClaims(List<ValuePairDao> daos, Document document);
    
    List<User> updateDocumentReviewerClaims(List<ValuePairDao> daos, Document document);
}
