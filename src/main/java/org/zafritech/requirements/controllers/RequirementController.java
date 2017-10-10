/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.controllers;

import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.services.UserStateService;

/**
 *
 * @author LukeS
 */
@Controller
public class RequirementController {
    
    @Autowired
    private DocumentRepository documentRepository;
      
    @Autowired
    private UserStateService stateService;
    
    @RequestMapping("/requirements")
    public String page(Model model) {
        
        model.addAttribute("requirements", "requirements");
        
        return null;
    }
    
    @RequestMapping("/requirements/document/{uuid}")
    public String getRequirementsDocument(@PathVariable(value = "uuid") String uuid, 
                                          Model model) {
        
        Document document = documentRepository.findByUuId(uuid);
        
        // Update last access
        document.setLastAccessed(new Timestamp(System.currentTimeMillis()));
        documentRepository.save(document);

        stateService.updateRecentDocument(document); 
               
        model.addAttribute("document", document);
        model.addAttribute("descriptor", document.getContentDescriptor().getDescriptorCode());

        return "views/documents/zid-document-fragment";
    }
}
