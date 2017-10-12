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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.dao.EntityTypeDao;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.repositories.EntityTypeRepository;

/**
 *
 * @author LukeS
 */
@Component
public class EntityTypesInit {
    
    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;
     
    @Transactional
    public void init() {
        
        String dataFile = data_dir + "initialisation/entity-types.json";
        
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            
            List<EntityTypeDao> json = Arrays.asList(mapper.readValue(new File(dataFile), EntityTypeDao[].class));
            
            for (EntityTypeDao dao : json) {
                
                EntityType type = new EntityType(dao.getKey(), dao.getName(), dao.getCode(), dao.getDescription()); 
                entityTypeRepository.save(type);
            }
            
        } catch (IOException ex) {
            
            Logger.getLogger(CountriesDataInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
