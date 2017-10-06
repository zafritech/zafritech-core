/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserEntityState;
import org.zafritech.core.data.domain.UserEntityStateKey;
import org.zafritech.core.data.repositories.UserEntityStateRepository;
import org.zafritech.core.enums.UserEntityTypes;
import org.zafritech.core.services.UserService;
import org.zafritech.core.services.UserStateService;

/**
 *
 * @author LukeS
 */
@Service
public class UserStateServiceImpl implements UserStateService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserEntityStateRepository stateRepository;
            
    @Override
    public void updateOpenProject(Project project) {

        UserEntityStateKey pk = new UserEntityStateKey(userService.loggedInUser().getId(), 
                                                     UserEntityTypes.ENTITY_PROJECT_OPEN, 
                                                     project.getId());
        
        UserEntityState state = stateRepository.findByStateKey(pk);
        
        if (state == null) {
            
            UserEntityState newState = new UserEntityState(pk);
            stateRepository.save(newState);
        }
    }

    @Override
    public void updateCloseProject(Project project) {
        
        UserEntityStateKey pk = new UserEntityStateKey(userService.loggedInUser().getId(), 
                                                       UserEntityTypes.ENTITY_PROJECT_OPEN, 
                                                       project.getId());
        
        UserEntityState state = stateRepository.findByStateKey(pk);
        
        if (state != null) {
            
            stateRepository.delete(state); 
        }
    }

    @Override
    public void updateRecentDocument(Document document) {
        
        UserEntityStateKey pk = new UserEntityStateKey(userService.loggedInUser().getId(), 
                                                       UserEntityTypes.ENTITY_DOCUMENT_RECENT, 
                                                       document.getId());
        
        UserEntityState state = stateRepository.findByStateKey(pk);
        
        if (state == null) {
            
            UserEntityState newState = new UserEntityState(pk);
            stateRepository.save(newState);
        }
        
        List<UserEntityState> documents = stateRepository.findByStateKeyUserIdAndStateKeyEntityTypeOrderByUpdateDateDesc(userService.loggedInUser().getId(), 
                                                                                                                         UserEntityTypes.ENTITY_DOCUMENT_RECENT);
        
        Integer count = 0;
        
        if (!documents.isEmpty()) {
            
            // Keep only 5 documents in the recent list
            for(UserEntityState docState : documents) {
                
                count++;
                
                if (count > 5) {

                    stateRepository.delete(docState); 
                }
            }
        }
    }

    @Override
    public List<UserEntityState> getOpenProjects() {
        
        List<UserEntityState> states = stateRepository.findByStateKeyUserIdAndStateKeyEntityType(userService.loggedInUser().getId(), 
                                                                                                 UserEntityTypes.ENTITY_PROJECT_OPEN);
        
        return states;
    }

    @Override
    public List<UserEntityState> getRecentDocuments() {
        
        List<UserEntityState> states = stateRepository.findByStateKeyUserIdAndStateKeyEntityType(userService.loggedInUser().getId(), 
                                                                                                 UserEntityTypes.ENTITY_DOCUMENT_RECENT);
        
        return states;
    }
}
