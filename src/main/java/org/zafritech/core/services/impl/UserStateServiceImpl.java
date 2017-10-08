/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.UserEntityState;
import org.zafritech.core.data.domain.UserEntityStateKey;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
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
    private ProjectRepository projectRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
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
            
        } else {
            
            state.setUpdateDate(new Timestamp(System.currentTimeMillis())); 
            stateRepository.save(state);
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
    public List<Project> getOpenProjects() {
        
        List<UserEntityState> states = stateRepository.findByStateKeyUserIdAndStateKeyEntityType(userService.loggedInUser().getId(), 
                                                                                                 UserEntityTypes.ENTITY_PROJECT_OPEN);
        
        List<Project> projects = new ArrayList<>();
        
        for (UserEntityState state : states) {
            
            projects.add(projectRepository.findOne(state.getStateKey().getEntityId())); 
        }
        
        return projects;
    }

    @Override
    public List<Document> getRecentDocuments() {
        
        List<UserEntityState> states = stateRepository.findByStateKeyUserIdAndStateKeyEntityTypeOrderByUpdateDateDesc(userService.loggedInUser().getId(), 
                                                                                                                      UserEntityTypes.ENTITY_DOCUMENT_RECENT);
        
        List<Document> documents = new ArrayList<>();
        
        for (UserEntityState state : states) {
            
            documents.add(documentRepository.findOne(state.getStateKey().getEntityId())); 
        }
        
        return documents;
    }

    @Override
    public boolean isProjectOpen(Project project) {
        
        boolean open = false;
        
        List<UserEntityState> states = stateRepository.findByStateKeyUserIdAndStateKeyEntityType(userService.loggedInUser().getId(), 
                                                                                                 UserEntityTypes.ENTITY_PROJECT_OPEN);
        
        for (UserEntityState state : states) {
            
            if (Objects.equals(state.getStateKey().getEntityId(), project.getId())) {
                
                open = true;
            }
        }
        
        return open;
    }

    @Override
    public Integer closeAllProjects() {
        
        Integer closed = 0;
        
        List<UserEntityState> states = stateRepository.findByStateKeyUserIdAndStateKeyEntityType(userService.loggedInUser().getId(), 
                                                                                                 UserEntityTypes.ENTITY_PROJECT_OPEN);
        
        for (UserEntityState state : states) {
            
            stateRepository.delete(state);
            closed++;
        }
        
        return closed;
    }
}
