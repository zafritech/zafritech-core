/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.SystemVariable;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
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
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @Transactional
    public void init() {
        
        Iterable<Document> documents = documentRepository.findAll();
        
        for (Document document : documents) {
            
            String wbs = document.getWbs().getWbsCode();
            String uuidTemplate = "ID" + document.getProject().getNumericNumber() + "-" + wbs + "-" + document.getDocumentType().getEntityTypeCode() + "-";
            String reqIdTemplate = "R" + document.getProject().getNumericNumber() + "-" + wbs + "-";
            
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.ITEM_UUID_NUMERIC_DIGITS.name(), "5", "DOCUMENT", document.getId()));
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_NUMERIC_DIGITS.name(), "5", "DOCUMENT", document.getId()));
            
            sysvarRepository.save(new SystemVariable(SystemVariableTypes.ITEM_UUID_TEMPLATE.name(), uuidTemplate, "DOCUMENT", document.getId()));
            
            List<EntityType> entityTypes = entityTypeRepository.findByEntityTypeKeyOrderByEntityTypeNameAsc("ITEM_TYPE_ENTITY");
            
            for (EntityType type : entityTypes) {
                
                sysvarRepository.save(new SystemVariable(SystemVariableTypes.REQUIREMENT_ID_TEMPLATE.name(), reqIdTemplate + type.getEntityTypeCode() + "-", "DOCUMENT", document.getId()));
            }
        }
    }
}
