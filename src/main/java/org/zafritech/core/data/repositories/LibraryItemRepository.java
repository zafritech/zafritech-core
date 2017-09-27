/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.LibraryItem;

/**
 *
 * @author LukeS
 */
public interface LibraryItemRepository extends CrudRepository<LibraryItem, Long> {
    
    LibraryItem findByUuId(String uuid);
    
    List<LibraryItem> findByFolder(Folder folder);
    
    List<LibraryItem> findByFolderOrderByItemTitleAsc(Folder folder);
}
