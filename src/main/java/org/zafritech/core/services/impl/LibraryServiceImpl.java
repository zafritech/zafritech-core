/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.LibraryItemDao;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.LibraryItem;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.LibraryItemRepository;
import org.zafritech.core.enums.LibraryItemTypes;
import org.zafritech.core.services.FileIOService;
import org.zafritech.core.services.LibraryService;

/**
 *
 * @author LukeS
 */
@Service
public class LibraryServiceImpl implements LibraryService {

    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
    
    @Value("${zafritech.paths.images-dir}")
    private String images_dir;
    
    @Autowired
    private FolderRepository folderRepository;
   
    @Autowired
    private LibraryItemRepository libraryItemRepository;
    
    @Autowired
    private FileIOService fileIOService;
    
    @Override
    public LibraryItem createLibraryItem(LibraryItemDao dao) throws IOException, ParseException {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String identifier = cleanUniqueIdentString(dao.getUniqueID());
        String refDataFolderPath = getFolderRelativePath(dao.getFolderId());
        
        // Upload and move Reference Item file
        String itemFileExtension = FilenameUtils.getExtension(dao.getItemFile().getOriginalFilename());
        String itemRelPath = refDataFolderPath + "/lib_" + identifier + "." + itemFileExtension;
        String itemFullPath = data_dir + itemRelPath;
        List<String> itemFiles = fileIOService.saveUploadedFiles(Arrays.asList(dao.getItemFile()));
        FileUtils.moveFile(FileUtils.getFile(itemFiles.get(0)), FileUtils.getFile(itemFullPath)); 
        
        // Upload and move Reference Image file
        String imageFileExtension = FilenameUtils.getExtension(dao.getImageFile().getOriginalFilename());
        String imageRelPath = "library/img_" + identifier + "." + imageFileExtension;
        String imageFullPath = images_dir + imageRelPath;
        List<String> imageFiles = fileIOService.saveUploadedFiles(Arrays.asList(dao.getImageFile()));
        FileUtils.moveFile(FileUtils.getFile(imageFiles.get(0)), FileUtils.getFile(imageFullPath)); 
        
        LibraryItem libraryItem = new LibraryItem(folderRepository.findOne(dao.getFolderId()),  
                                                  LibraryItemTypes.valueOf(dao.getReferenceType()),  
                                                  dao.getUniqueID(), 
                                                  dao.getAuthors(), 
                                                  dao.getPublisher(), 
                                                  dao.getItemTitle(), 
                                                  dao.getItemSummary(), 
                                                  itemRelPath,          
                                                  imageRelPath,     
                                                  itemFileExtension.toUpperCase(), 
                                                  new Date(dateFormatter.parse(dao.getPubDate()).getTime()),  
                                                  dao.getRevision(), 
                                                  "");              // Keywords - empty for now
        
        libraryItem = libraryItemRepository.save(libraryItem);
        
        return libraryItem;
    }
    
    private String cleanUniqueIdentString(String uniqueID) {
        
        return uniqueID.replace(" ", "-").replace("/", "_").replace(":", "-").toLowerCase();
    }
    
    private String getFolderRelativePath(Long id) {
        
        Folder folder;
        folder = folderRepository.findOne(id);
        Folder parent = folder.getParent();
        String path = folder.getFolderName().replace(" ", "_").replace("/", "_").toLowerCase();
        
        while(parent != null) {
            
            folder = folderRepository.findOne(parent.getId());
            path = folder.getFolderName().replace(" ", "_").replace("/", "_").toLowerCase() + "/" + path;
            
            parent = folder.getParent();
        }
        
        return path;
    }
}
