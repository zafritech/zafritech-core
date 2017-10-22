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
import org.zafritech.core.data.domain.UserSessionEntity;
import org.zafritech.core.data.domain.UserSessionEntityKey;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.enums.UserSessionEntityTypes;
import org.zafritech.core.services.UserService;
import org.zafritech.core.data.repositories.UserSessionEntityRepository;
import org.zafritech.core.services.UserSessionService;

/**
 *
 * @author LukeS
 */
@Service
public class UserSessionServiceImpl implements UserSessionService {

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserSessionEntityRepository stateRepository;
            
    @Override
    public void updateOpenProject(Project project) {

        UserSessionEntityKey pk = new UserSessionEntityKey(userService.loggedInUser().getId(), 
                                                     UserSessionEntityTypes.ENTITY_PROJECT_OPEN, 
                                                     project.getId());
        
        UserSessionEntity state = stateRepository.findBySessionKey(pk);
        
        if (state == null) {
            
            UserSessionEntity newState = new UserSessionEntity(pk);
            stateRepository.save(newState);
        }
    }
       
    @Override
    public Project getLastOpenProject() {

        UserSessionEntity lastOpenProjectsession = stateRepository.findFirstBySessionKeyUserIdAndSessionKeyEntityTypeOrderByUpdateDateDesc(
                                                        userService.loggedInUser().getId(),
                                                        UserSessionEntityTypes.ENTITY_PROJECT_OPEN
                                                   );
        
        Project project = null;
        
        if (lastOpenProjectsession != null) {
            
            project = projectRepository.findOne(lastOpenProjectsession.getSessionKey().getEntityId()); 
        }
        
        return project;
    }

    @Override
    public void updateCloseProject(Project project) {
        
        UserSessionEntityKey pk = new UserSessionEntityKey(userService.loggedInUser().getId(), 
                                                       UserSessionEntityTypes.ENTITY_PROJECT_OPEN, 
                                                       project.getId());
        
        UserSessionEntity state = stateRepository.findBySessionKey(pk);
        
        if (state != null) {
            
            stateRepository.delete(state); 
        }
    }
    
    @Override
    public void updateOpenDocument(Document document) {
        
        UserSessionEntityKey pk = new UserSessionEntityKey(userService.loggedInUser().getId(), 
                                                       UserSessionEntityTypes.ENTITY_DOCUMENT_OPEN, 
                                                       document.getId());
        
        UserSessionEntity state = stateRepository.findBySessionKey(pk);
        
        if (state == null) {
            
            UserSessionEntity newState = new UserSessionEntity(pk);
            stateRepository.save(newState);
        }
    }
   
    @Override
    public void updateCloseDocument(Document document) {
        
        UserSessionEntityKey pk = new UserSessionEntityKey(userService.loggedInUser().getId(), 
                                                       UserSessionEntityTypes.ENTITY_DOCUMENT_OPEN, 
                                                       document.getId());
        
        UserSessionEntity state = stateRepository.findBySessionKey(pk);
        
        if (state != null) {
            
            stateRepository.delete(state); 
        }
    }

    @Override
    public void updateRecentDocument(Document document) {
        
        UserSessionEntityKey pk = new UserSessionEntityKey(userService.loggedInUser().getId(), 
                                                       UserSessionEntityTypes.ENTITY_DOCUMENT_RECENT, 
                                                       document.getId());
        
        UserSessionEntity state = stateRepository.findBySessionKey(pk);
        
        if (state == null) {
            
            UserSessionEntity newState = new UserSessionEntity(pk);
            stateRepository.save(newState);
            
        } else {
            
            state.setUpdateDate(new Timestamp(System.currentTimeMillis())); 
            stateRepository.save(state);
        }
        
        List<UserSessionEntity> documents = stateRepository.findBySessionKeyUserIdAndSessionKeyEntityTypeOrderByUpdateDateDesc(userService.loggedInUser().getId(), 
                                                                                                                         UserSessionEntityTypes.ENTITY_DOCUMENT_RECENT);
        
        Integer count = 0;
        
        if (!documents.isEmpty()) {
            
            // Keep only 5 documents in the recent list
            for(UserSessionEntity docState : documents) {
                
                count++;
                
                if (count > 5) {

                    stateRepository.delete(docState); 
                }
            }
        }
    }

    @Override
    public List<Project> getOpenProjects() {
        
        List<UserSessionEntity> states = stateRepository.findBySessionKeyUserIdAndSessionKeyEntityType(userService.loggedInUser().getId(), 
                                                                                                 UserSessionEntityTypes.ENTITY_PROJECT_OPEN);
        
        List<Project> projects = new ArrayList<>();
        
        for (UserSessionEntity state : states) {
            
            projects.add(projectRepository.findOne(state.getSessionKey().getEntityId())); 
        }
        
        return projects;
    }
    
    @Override
    public List<Document> getOpenDocuments() {
        
        List<UserSessionEntity> states = stateRepository.findBySessionKeyUserIdAndSessionKeyEntityType(userService.loggedInUser().getId(), 
                                                                                                 UserSessionEntityTypes.ENTITY_DOCUMENT_OPEN);
        
        List<Document> documents = new ArrayList<>();
        
        for (UserSessionEntity state : states) {
            
            documents.add(documentRepository.findOne(state.getSessionKey().getEntityId())); 
        }
         
        return documents;
    }

    @Override
    public List<Document> getRecentDocuments() {
        
        List<UserSessionEntity> states = stateRepository.findBySessionKeyUserIdAndSessionKeyEntityTypeOrderByUpdateDateDesc(userService.loggedInUser().getId(), 
                                                                                                                      UserSessionEntityTypes.ENTITY_DOCUMENT_RECENT);
        
        List<Document> documents = new ArrayList<>();
        
        for (UserSessionEntity state : states) {
            
            documents.add(documentRepository.findOne(state.getSessionKey().getEntityId())); 
        }
        
        return documents;
    }

    @Override
    public boolean isProjectOpen(Project project) {
        
        boolean open = false;
        
        List<UserSessionEntity> states = stateRepository.findBySessionKeyUserIdAndSessionKeyEntityType(userService.loggedInUser().getId(), 
                                                                                                 UserSessionEntityTypes.ENTITY_PROJECT_OPEN);
        
        for (UserSessionEntity state : states) {
            
            if (Objects.equals(state.getSessionKey().getEntityId(), project.getId())) {
                
                open = true;
            }
        }
        
        return open;
    }

    @Override
    public Integer closeAllProjects() {
        
        Integer closed = 0;
        
        List<UserSessionEntity> states = stateRepository.findBySessionKeyUserIdAndSessionKeyEntityType(userService.loggedInUser().getId(), 
                                                                                                 UserSessionEntityTypes.ENTITY_PROJECT_OPEN);
        
        for (UserSessionEntity state : states) {
            
            stateRepository.delete(state);
            closed++;
        }
        
        return closed;
    }
}
