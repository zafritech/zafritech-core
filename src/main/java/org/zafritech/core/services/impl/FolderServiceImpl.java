/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.FolderTreeDao;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.InformationClassRepository;
import org.zafritech.core.services.FolderService;
import org.zafritech.core.services.UserService;

/**
 *
 * @author LukeS
 */
@Service
public class FolderServiceImpl implements FolderService {

    @Autowired
    private EntityTypeRepository entityTypeRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private DocumentRepository documentRepository;
     
    @Autowired
    private InformationClassRepository infoClassRepository;
   
    @Autowired
    private UserService userService;
     
    @Override
    public List<FolderTreeDao> getProjectFolders(Project project) {
        
        List<Folder> folders = folderRepository.findByProjectOrderBySortIndexAsc(project);
        List<FolderTreeDao> folderTree = new ArrayList<>();
        
        for (Folder folder : folders) {
            
            if (folder.getFolderType().equals(entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_PROJECT"))) { 
                
                folderTree.add(new FolderTreeDao(
                
                        folder.getId(),
                        (folder.getParent() != null) ? folder.getParent().getId() : 0L,
                        folder.getFolderName(),
                        (folder.getParent() == null),
                        true,
                        true,
                        "/images/icons/db-icon.png",
                        project.getId()
                ));
                
            } else {
                
                folderTree.add(new FolderTreeDao(
                
                        folder.getId(),
                        (folder.getParent() != null) ? folder.getParent().getId() : 0L,
                        folder.getFolderName(),
                        (folder.getParent() == null),
                        true,
                        true
                ));
            }
        }
        
        return folderTree;
    }

    @Override
    public List<FolderTreeDao> getProjectDocuments(Project project) {
        
        List<Document> docs = documentRepository.findByProject(project);
        List<FolderTreeDao> projectDocs = new ArrayList<>();
        
        for (Document doc : docs) {
             
            projectDocs.add(new FolderTreeDao(
                    
                    doc.getId() + 5000,             // Prevent TreeNodes id classes with folders
                    doc.getFolder().getId(),
                    doc.getIdentifier(),
                    false,
                    false,
                    true,
                    doc.getId()
            ));
        }
        
        return projectDocs;
    }

    @Override
    public Folder duplicateTree(Long id) {
        
        Folder folder = folderRepository.findOne(id);
        Folder parent = folder.getParent();
        
        return copyFolder(folder, parent);
    }
    
    private Folder copyFolder(Folder rootFolder, Folder parentFolder) {
        
        List<Document> documents = documentRepository.findByFolder(rootFolder); 
        List<Folder> folders = folderRepository.findByParentOrderBySortIndexAsc(rootFolder);
        
        Folder nFolder = saveCopiedFolder(rootFolder, parentFolder, documents);
        
        for (Folder folder : folders) {
            
            copyFolder(folder, nFolder);
        }
        
        return nFolder;
    }
    
    private Folder saveCopiedFolder(Folder oFolder, Folder parentFolder, List<Document> documents) {
        
        Folder folder = folderRepository.save(new Folder(
        
                oFolder.getFolderName() + " - Copy",
                oFolder.getFolderType(),
                parentFolder,
                oFolder.getProject(),
                oFolder.getSortIndex()
        ));
        
        for (Document doc : documents) {
            
            Document newDoc = new Document(
            
                    doc.getIdentifier() + "-COPY",
                    doc.getDocumentName() + " - Empty",
                    doc.getDocumentType(),
                    doc.getContentDescriptor(),
                    doc.getProject(),
                    doc.getWbs(),
                    folder,
                    infoClassRepository.findByClassCode("INFO_OFFICIAL"),
                    "0A"
            );
            
            newDoc.setOwner(userService.loggedInUser());
            
            documentRepository.save(newDoc);
        }
        
        return folder;
    }

    @Override
    public List<FolderTreeDao> getLibraryFolders() {
        
        List<FolderTreeDao> foldersTree = new ArrayList<>();
        
        EntityType folderType = entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_LIBRARY");
        List<Folder> folders = folderRepository.findByFolderTypeOrderBySortIndexAsc(folderType);
        
        for (Folder folder : folders) {

            if (folder.getParent() == null) { 
                
                foldersTree.add(new FolderTreeDao(
                
                        folder.getId(),
                        (folder.getParent() != null) ? folder.getParent().getId() : 0L,
                        folder.getFolderName(),
                        (folder.getParent() == null),
                        true,
                        true,
                        "/images/icons/books-icon.png",
                        null
                ));
                
            } else {

                foldersTree.add(new FolderTreeDao(

                        folder.getId(),
                        (folder.getParent() != null) ? folder.getParent().getId() : 0L,
                        folder.getFolderName(),
                        (folder.getParent() == null),
                        true,
                        true
                ));
            }
        }
       
        return foldersTree;
    }
}
