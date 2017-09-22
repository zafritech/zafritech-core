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
import org.zafritech.core.data.domain.InformationClass;
import org.zafritech.core.data.repositories.InformationClassRepository;

/**
 *
 * @author LukeS
 */
@Component
public class InfoClassesInit {
    
    @Autowired
    private InformationClassRepository infoClassRepository;
    
    @Transactional
    public void init() {
        
        infoClassRepository.save(new HashSet<InformationClass>() {
            
            {
                add(new InformationClass("Unclassified", "INFO_UNCLASSIFIED"));
                add(new InformationClass("Open Source", "INFO_OPEN"));
                add(new InformationClass("Official", "INFO_OFFICIAL"));
                add(new InformationClass("Confidential", "INFO_CONFIDENTIAL"));
                add(new InformationClass("Restricted", "INFO_RESTRICTED"));
                add(new InformationClass("Secret", "INFO_SECRET"));
            }
        });
    }
}
