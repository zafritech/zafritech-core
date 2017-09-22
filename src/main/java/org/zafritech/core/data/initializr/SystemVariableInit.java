/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.SystemVariable;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.SystemVariableRepository;
import org.zafritech.core.enums.SystemVariableTypes;

/**
 *
 * @author LukeS
 */
@Component
public class SystemVariableInit {
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private SystemVariableRepository sysvarRepository;
    
    @Transactional
    public void init() {
        
        Iterable<Document> documents = documentRepository.findAll();
        
        for (Document document : documents) {
            
            String uuidTemplate = document.getDocumentName().substring(0, 3).toUpperCase() + "-ID" + String.format("%02d", document.getId());
            String reqIdTemplate = document.getDocumentName().substring(0, 2).toUpperCase() + String.format("%02d", document.getId());
            
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.ITEM_UUID_NUMERIC_DIGITS.name(), "4", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_NUMERIC_DIGITS.name(), "4", "DOCUMENT", document.getId()));
            
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.ITEM_UUID_TEMPLATE.name(), uuidTemplate, "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + "-GENL", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + "-INTF", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + "-FCNL", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + "-STMD", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + "-ENVR", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + "-PERF", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + "-RSRC", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + "-PHCL", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + "-DSGN", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + "-CNST", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + "-QLTY", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + "-SAFT", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + "-VAVN", "DOCUMENT", document.getId()));

        }
    }
}
