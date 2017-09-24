/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import java.util.HashSet;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.repositories.FolderRepository;
import org.zafritech.core.data.repositories.FolderTypeRepository;

/**
 *
 * @author LukeS
 */
@Component
public class LibraryInit {
    
    @Autowired
    private FolderRepository folderRepository;
    
    @Autowired
    private FolderTypeRepository folderTypeRepository;
    
    @Transactional
    public void init() {
        
        Folder library = folderRepository.save(new Folder("Library", folderTypeRepository.findByTypeKey("FOLDER_LIBRARY"), null, null, 0)); 
        
        folderRepository.save(new Folder("Books", folderTypeRepository.findByTypeKey("FOLDER_LIBRARY"), library, null, 0));
        
        Folder standards = folderRepository.save(new Folder("Standards", folderTypeRepository.findByTypeKey("FOLDER_LIBRARY"), library, null, 1));
        folderRepository.save(new Folder("Company", folderTypeRepository.findByTypeKey("FOLDER_LIBRARY"), standards, null, 0));
        folderRepository.save(new Folder("EIA", folderTypeRepository.findByTypeKey("FOLDER_LIBRARY"), standards, null, 1));
        folderRepository.save(new Folder("Euro Norm", folderTypeRepository.findByTypeKey("FOLDER_LIBRARY"), standards, null, 2));
        folderRepository.save(new Folder("ISO/IEC/IEEE", folderTypeRepository.findByTypeKey("FOLDER_LIBRARY"), standards, null, 3));
        folderRepository.save(new Folder("MIL_STD", folderTypeRepository.findByTypeKey("FOLDER_LIBRARY"), standards, null, 4));
        folderRepository.save(new Folder("Other", folderTypeRepository.findByTypeKey("FOLDER_LIBRARY"), standards, null, 5));
    }
}
