/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.core.data.dao.FolderTreeDao;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.LibraryItem;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.FolderTypeRepository;
import org.zafritech.core.data.repositories.LibraryItemRepository;
import org.zafritech.core.services.FolderService;

/**
 *
 * @author LukeS
 */
@RestController
public class LibraryRestController {
   
    @Autowired
    private FolderRepository folderRepository;
   
    @Autowired
    private LibraryItemRepository libraryItemRepository;
    
    @Autowired
    private FolderService folderService;
    
    @RequestMapping(value = "/api/library/folders/tree/list", method = GET)
    public List<FolderTreeDao> getLibraryFolderTree() {
        
        List<FolderTreeDao> foldersTree = new ArrayList<>();
        
        List<FolderTreeDao> folders = folderService.getLibraryFolders();
        
        foldersTree.addAll(folders);
        
        return foldersTree;
    }
    
    @RequestMapping(value = "/api/library/folder/items/{id}", method = GET)
    public ResponseEntity<List<LibraryItem>> getLibraryFolderItems(@PathVariable Long id) {
        
        List<LibraryItem> libraryItems = libraryItemRepository.findByFolder(folderRepository.findOne(id)); 
                
        return new ResponseEntity<List<LibraryItem>>(libraryItems, HttpStatus.OK);
    }
}
