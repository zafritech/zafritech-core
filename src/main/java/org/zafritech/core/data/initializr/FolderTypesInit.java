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
import org.zafritech.core.data.domain.FolderType;
import org.zafritech.core.data.repositories.FolderTypeRepository;

/**
 *
 * @author LukeS
 */
@Component
public class FolderTypesInit {
    
    @Autowired
    private FolderTypeRepository folderTypeRepository;
    
    @Transactional
    public void init() {
        
        folderTypeRepository.save(new HashSet<FolderType>() {{
            
                add(new FolderType("Company", "FOLDER_COMPANY"));
                add(new FolderType("Project", "FOLDER_PROJECT"));
                add(new FolderType("Document", "FOLDER_DOCUMENT"));
                add(new FolderType("Task", "FOLDER_TASK"));
                add(new FolderType("User", "FOLDER_USER"));
                add(new FolderType("General", "FOLDER_GENERAL"));
            }
        });
    }
}
