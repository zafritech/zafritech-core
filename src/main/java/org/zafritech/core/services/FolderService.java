/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.zafritech.core.data.dao.FolderDao;
import org.zafritech.core.data.dao.FolderTreeDao;
import org.zafritech.core.data.dao.ValuePairDao;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author LukeS
 */
public interface FolderService {
    
    Folder createFolder(FolderDao folderDao);
    
    Folder renameFolder(ValuePairDao vpDao, Long id);
    
    List<FolderTreeDao> getProjectFolders(Project project);
    
    List<FolderTreeDao> getProjectDocuments(Project project);
    
    List<FolderTreeDao> getProjectFoldersTree(Long projectId);
    
    List<FolderTreeDao> getOpenProjectFoldersTree();
            
    Folder duplicateTree(Long id); 
    
    List<FolderTreeDao> getLibraryFolders();
    
    Integer getFolderContentsCount(Long folderId);
}
