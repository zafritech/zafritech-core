/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.dao.DocumentTypeDao;
import org.zafritech.core.data.domain.DocumentContentDescriptor;
import org.zafritech.core.data.domain.DocumentType;
import org.zafritech.core.data.repositories.DocumentContentDescriptorRepository;
import org.zafritech.core.data.repositories.DocumentTypeRepository;

/**
 *
 * @author LukeS
 */
@Component
public class DocumentTypeInit {
      
    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
    
    @Autowired
    private DocumentTypeRepository documentTypeRepository;
    
    @Autowired
    private DocumentContentDescriptorRepository descriptorRepository;
    
    @Transactional
    public void init() throws IOException {
  
        String dataFile = data_dir + "json/document-types.json";
             
        ObjectMapper mapper = new ObjectMapper();
        
        List<DocumentTypeDao> jsonTypes = Arrays.asList(mapper.readValue(new File(dataFile), DocumentTypeDao[].class)); 
        DocumentContentDescriptor descriptor = descriptorRepository.findByDescriptorCode("CONTENT_TYPE_REQUIREMENTS");
        
        for (DocumentTypeDao dao : jsonTypes) {
            
            DocumentType docType = new DocumentType(dao.getCode(), dao.getName(), descriptor, dao.getDescription());
            documentTypeRepository.save(docType);
        }
    }
}
