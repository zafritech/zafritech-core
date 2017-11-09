/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.api.core;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.zafritech.core.data.dao.FolderDao;
import org.zafritech.core.data.dao.FolderTreeDao;
import org.zafritech.core.data.dao.ValuePairDao;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.services.FolderService;

/**
 *
 * @author LukeS
 */
@RestController
public class FolderRestController {
   
    @Autowired
    private FolderRepository folderRepository;
   
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @Autowired
    private FolderService folderService;
     
    @RequestMapping(value = "/api/admin/folders/create/new", method = POST)
    public ResponseEntity<Folder> folderCreateNew(@RequestBody FolderDao folderDao) {
        
        return new ResponseEntity<Folder>(folderService.createFolder(folderDao), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/folders/folder/duplicate/subtree/{id}", method = GET)
    public ResponseEntity<Folder> duplicateFolderSubtree(@PathVariable(value = "id") Long id) {
        
        return new ResponseEntity<Folder>(folderService.duplicateTree(id), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/folders/rename/{id}", method = POST)
    public ResponseEntity<Folder> folderRename(@RequestBody ValuePairDao vpDao,
                                               @PathVariable(value = "id") Long id) {
        
        return new ResponseEntity<Folder>(folderService.renameFolder(vpDao, id), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/folders/tree/{id}", method = GET)
    public List<FolderTreeDao> getProjectFoldersTree(@PathVariable Long id) {
        
        return folderService.getProjectFoldersTree(id);
    }
    
    @RequestMapping(value = "/api/folders/projects/tree", method = GET)
    public List<FolderTreeDao> getOpenProjectsFolderTree() {
        
        return folderService.getOpenProjectFoldersTree();
    }
    
    @RequestMapping(value = "/api/folders/folder/uuid/{id}", method = GET, produces="text/plain")
    public ResponseEntity<String> getFolderUuuId(@PathVariable Long id) {
        
        return new ResponseEntity<String>(folderRepository.findOne(id).getUuId(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/folders/project/uuid/{id}", method = GET, produces="text/plain")
    public ResponseEntity<String> getProjectUuuIdByFolderId(@PathVariable Long id) {
        
        Folder folder = folderRepository.findOne(id);
        String uuId =  folder.getProject().getUuId();
        
        return new ResponseEntity<String>(uuId, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/folders/folder/{id}", method = GET)
    public ResponseEntity<Folder> getFolderById(@PathVariable Long id) {
        
        Folder folder =  folderRepository.findOne(id);
        
        return new ResponseEntity<Folder>(folder, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/folders/folder/emptycheck/{id}", method = GET)
    public ResponseEntity<Integer> checkFolderIsEmpty(@PathVariable Long id) {
        
        return new ResponseEntity<Integer>(folderService.getFolderContentsCount(id), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/folders/foldertypes/list", method = GET)
    public ResponseEntity<List<EntityType>> listFolderTypes() {
        
        List<EntityType> folderTypes = entityTypeRepository.findByEntityTypeKeyOrderByEntityTypeNameAsc("FOLDER_TYPE_ENTITY"); 
        
        return new ResponseEntity<List<EntityType>>(folderTypes, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/folders/folder/delete/{id}", method = GET)
    public ResponseEntity<Integer> deleteDocument(@PathVariable Long id) {
        
        folderRepository.delete(id);
        
        return new ResponseEntity<Integer> (1, HttpStatus.OK);
    }
}
