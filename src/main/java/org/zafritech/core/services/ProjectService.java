/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import java.util.List;
import org.zafritech.core.data.dao.ProjectDao;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;

/**
 *
 * @author LukeS
 */
public interface ProjectService {
    
    Project saveDao(ProjectDao dao);
    
    User addMemberToProject(Project project, User user);
    
    List<User> addProjectMembers(Project project, List<User> users);
    
    String generateProjectNumber(EntityType type);
}
