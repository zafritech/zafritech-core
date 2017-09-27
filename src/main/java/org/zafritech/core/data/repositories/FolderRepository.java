/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.FolderType;
import org.zafritech.core.data.domain.Project;

/**
 *
 * @author LukeS
 */
public interface FolderRepository extends CrudRepository<Folder, Long> {
    
    Folder findByUuId(String uuid);
    
    List<Folder> findByProject(Project project);
    
    List<Folder> findByProjectOrderBySortIndexAsc(Project project);
    
    Folder findFirstByProjectAndFolderType(Project project, FolderType type);
    
    List<Folder> findByParent(Folder parent);
    
    List<Folder> findByParentOrderByFolderNameAsc(Folder parent);
    
    List<Folder> findByParentOrderBySortIndexAsc(Folder parent);
    
    List<Folder> findByFolderType(FolderType folderType);
    
    List<Folder> findByFolderTypeOrderBySortIndexAsc(FolderType folderType);
}
