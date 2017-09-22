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
import org.zafritech.core.data.domain.ProjectType;
import org.zafritech.core.data.repositories.ProjectTypeRepository;

/**
 *
 * @author LukeS
 */
@Component
public class ProjectTypesInit {
    
    @Autowired
    private ProjectTypeRepository projectTypeRepository;
    
    @Transactional
    public void init() {
        
        projectTypeRepository.save(new HashSet<ProjectType>() {{
            
                add(new ProjectType("CON", "Construction"));
                add(new ProjectType("FIN", "Finance and Investment"));
                add(new ProjectType("ICT", "Information Technology"));
                add(new ProjectType("PER", "Personal Development"));
                add(new ProjectType("PDT", "Product Development"));
                add(new ProjectType("SWD", "Software Development"));
                add(new ProjectType("TRN", "Training"));
            }
        });
    }
}
