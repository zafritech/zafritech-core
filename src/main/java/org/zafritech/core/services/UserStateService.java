/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import java.util.List;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.UserEntityState;

/**
 *
 * @author LukeS
 */
public interface UserStateService {
   
    void updateOpenProject(Project project);
   
    void updateCloseProject(Project project);
    
    void updateRecentDocument(Document document);
    
    List<UserEntityState> getOpenProjects();
    
    List<UserEntityState> getRecentDocuments();
}
