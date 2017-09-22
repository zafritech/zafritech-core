/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.FolderRepository;

/**
 *
 * @author LukeS
 */
@Controller
public class AdminFolderController {
    
    @Autowired
    private FolderRepository folderRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @RequestMapping("/admin/projects/folder/{uuid}")
    public String getFolder(@PathVariable String uuid, Model model) {
        
        Folder folder = folderRepository.findByUuId(uuid);
        List<Folder> folders = folderRepository.findByParent(folder);
        List<Document> documents = documentRepository.findByFolder(folder);
            
        model.addAttribute("project", folder.getProject());
        model.addAttribute("folder", folder);
        model.addAttribute("folders", folders);
        model.addAttribute("documents", documents);
        
        return "views/admin/project/folder";
    }
}
