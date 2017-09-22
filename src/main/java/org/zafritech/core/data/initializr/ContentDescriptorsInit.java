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
import org.zafritech.core.data.domain.DocumentContentDescriptor;
import org.zafritech.core.data.repositories.DocumentContentDescriptorRepository;

/**
 *
 * @author LukeS
 */
@Component
public class ContentDescriptorsInit {
  
    @Autowired
    private DocumentContentDescriptorRepository descriptorRepository;
    
    @Transactional
    public void init() {
        
        descriptorRepository.save(new HashSet<DocumentContentDescriptor>() {

            {
                add(new DocumentContentDescriptor("Generic Information", "CONTENT_TYPE_GENERIC", "Generic textual information"));
                add(new DocumentContentDescriptor("Project Information", "CONTENT_TYPE_PROJECT", "Project detail information"));
                add(new DocumentContentDescriptor("Tasks", "CONTENT_TYPE_TASKS", "Task list information"));
                add(new DocumentContentDescriptor("Requirements", "CONTENT_TYPE_REQUIREMENTS", "Requirements specification information"));
                add(new DocumentContentDescriptor("Requirements Link", "CONTENT_TYPE_LINKS", "Requirements links information"));
            }
        });
    }
}
