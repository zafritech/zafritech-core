/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.api.core;

import java.util.ArrayList;
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
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.ProjectRepository;
import org.zafritech.core.services.FolderService;
import org.zafritech.core.services.UserStateService;

/**
 *
 * @author LukeS
 */
@RestController
public class FolderRestController {
   
    @Autowired
    private ProjectRepository projectRepository;
   
    @Autowired
    private FolderRepository folderRepository;
   
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private FolderService folderService;
     
    @Autowired
    private UserStateService stateService;
    
    @RequestMapping(value = "/api/admin/folders/create/new", method = POST)
    public ResponseEntity<Folder> folderCreateNew(@RequestBody FolderDao folderDao) {
        
        Folder folder = folderRepository.save(new Folder(
                folderDao.getFolderName(),
                entityTypeRepository.findOne(folderDao.getFolderTypeId()),
                folderRepository.findOne(folderDao.getParentId()),
                projectRepository.findOne(folderDao.getProjectId()) 
        )); 

        return new ResponseEntity<Folder>(folder, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/folders/folder/duplicate/subtree/{id}", method = GET)
    public ResponseEntity<Folder> duplicateFolderSubtree(@PathVariable(value = "id") Long id) {
        
        Folder folder = folderService.duplicateTree(id); 

        return new ResponseEntity<Folder>(folder, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/folders/rename/{id}", method = POST)
    public ResponseEntity<Folder> folderRename(@RequestBody ValuePairDao vpDao,
                                               @PathVariable(value = "id") Long id) {
        
        Folder folder = folderRepository.findOne(id); 
        folder.setFolderName(vpDao.getItemName()); 
        folder = folderRepository.save(folder);
        
        return new ResponseEntity<Folder>(folder, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/folders/tree/{id}", method = GET)
    public List<FolderTreeDao> getFolderTree(@PathVariable Long id) {
        
        List<FolderTreeDao> foldersTree = new ArrayList<>();
        Project project = projectRepository.findOne(id);
        
        List<FolderTreeDao> folders = folderService.getProjectFolders(project);
        List<FolderTreeDao> docs = folderService.getProjectDocuments(project);
        
        foldersTree.addAll(folders);
        foldersTree.addAll(docs);
        
        return foldersTree;
    }
    
    @RequestMapping(value = "/api/folders/projects/tree", method = GET)
    public List<FolderTreeDao> getOpenProjectsFolderTree() {
        
        List<FolderTreeDao> foldersTree = new ArrayList<>();
        
        List<Project> openProjects = stateService.getOpenProjects();
        
        for (Project project : openProjects) {
            
            List<FolderTreeDao> folders = folderService.getProjectFolders(project);
            List<FolderTreeDao> docs = folderService.getProjectDocuments(project);

            foldersTree.addAll(folders);
            foldersTree.addAll(docs);
        }
        
        return foldersTree;
    }
    
    @RequestMapping(value = "/api/folders/folder/uuid/{id}", method = GET, produces="text/plain")
    public ResponseEntity<String> getFolderUuuId(@PathVariable Long id) {
        
        String uuId =  folderRepository.findOne(id).getUuId();
        
        return new ResponseEntity<String>(uuId, HttpStatus.OK);
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
        
        Folder parent =  folderRepository.findOne(id);
        List<Folder> folders = folderRepository.findByParent(parent);
        List<Document> documents = documentRepository.findByFolder(parent);
        Integer children = folders.size() + documents.size();
        
        return new ResponseEntity<Integer>(children, HttpStatus.OK);
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
