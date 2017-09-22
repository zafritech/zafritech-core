/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import java.util.List;
import org.zafritech.core.data.dao.FolderTreeDao;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author LukeS
 */
public interface FolderService {
    
    List<FolderTreeDao> getProjectFolders(Project project);
    
    List<FolderTreeDao> getProjectDocuments(Project project);
    
    Folder duplicateTree(Long id); 
}
