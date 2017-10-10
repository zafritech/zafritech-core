/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.zafritech.core.data.dao.FolderTreeDao;
import org.zafritech.core.data.dao.LibraryItemDao;
import org.zafritech.core.data.dao.ValuePairDao;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.LibraryItem;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.LibraryItemRepository;
import org.zafritech.core.services.FolderService;
import org.zafritech.core.services.LibraryService;

/**
 *
 * @author LukeS
 */
@RestController
public class LibraryRestController {
   
    @Autowired
    private FolderRepository folderRepository;
   
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @Autowired
    private LibraryItemRepository libraryItemRepository;
    
    @Autowired
    private FolderService folderService;
    
    @Autowired
    private LibraryService libraryService;
    
    @RequestMapping(value = "/api/library/folders/tree/list", method = GET)
    public List<FolderTreeDao> getLibraryFolderTree() {
        
        List<FolderTreeDao> foldersTree = new ArrayList<>();
        
        List<FolderTreeDao> folders = folderService.getLibraryFolders();
        
        foldersTree.addAll(folders);
        
        return foldersTree;
    }
    
    @RequestMapping(value = "/api/library/folder/items/get/{id}", method = GET)
    public ResponseEntity<List<LibraryItem>> getLibraryFolderItems(@PathVariable Long id) {
        
        List<LibraryItem> items = libraryItemRepository.findByFolderOrderByItemTitleAsc(folderRepository.findOne(id)); 

        return new ResponseEntity<List<LibraryItem>>(items, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/library/folder/items/list/{id}", method = GET)
    public ModelAndView getLibraryFolderModel(@PathVariable Long id) {
        
        List<Folder> folders = folderRepository.findByParentOrderByFolderNameAsc(folderRepository.findOne(id)); 
        List<LibraryItem> libraryItems = libraryItemRepository.findByFolderOrderByItemTitleAsc(folderRepository.findOne(id)); 
        
        ModelAndView modelView = new ModelAndView("views/library/library-items-list");
        modelView.addObject("folders", folders);
        modelView.addObject("items", libraryItems);
        
        return modelView;
    }
    
    @RequestMapping(value = "/api/library/reference/items/add", method = RequestMethod.POST)
    public ResponseEntity<?> addReferenceItem(LibraryItemDao libDao) throws IOException, ParseException {
        
        if (libDao.getItemFile().isEmpty()) {
            
            return new ResponseEntity("please select a reference file!", HttpStatus.OK);
        }
        
        if (libDao.getImageFile().isEmpty()) {
            
            return new ResponseEntity("please select a image file!", HttpStatus.OK);
        }
        
        LibraryItem libraryItem = libraryService.createLibraryItem(libDao);
        
        return new ResponseEntity("Successfully created reference item: " + libraryItem.getItemTitle(), new HttpHeaders(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/library/reference/folder/add", method = RequestMethod.POST)
    public ResponseEntity<?> addReferenceFolder(@RequestBody ValuePairDao dao) {
        
        System.out.println(dao);
        
        Folder folder = new Folder(dao.getItemName(), entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode("FOLDER_TYPE_ENTITY", "FOLDER_LIBRARY"), folderRepository.findOne(dao.getId()), null);
        folder = folderRepository.save(folder);
        
        return new ResponseEntity("Successfully created folder: " + folder.getFolderName(), new HttpHeaders(), HttpStatus.OK);
    }
}
