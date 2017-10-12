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
import org.zafritech.core.data.dao.TemplateVariableDao;
import org.zafritech.core.data.domain.TemplateVariable;
import org.zafritech.core.data.repositories.TemplateVariableRepository;
import org.zafritech.core.enums.TemplateVariableCategories;

/**
 *
 * @author LukeS
 */
@Component
public class TemplateVariablesInit {
    
    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
    
    @Autowired
    private TemplateVariableRepository variableRepository;
    
    @Transactional
    public void init() {
          
        String dataFile = data_dir + "initialisation/template-variables.json";
        
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            
            List<TemplateVariableDao> json = Arrays.asList(mapper.readValue(new File(dataFile), TemplateVariableDao[].class));
            
            for (TemplateVariableDao dao : json) {
                
                TemplateVariable var = new TemplateVariable(TemplateVariableCategories.valueOf(dao.getCategory()), dao.getVariable(), dao.getValue());
                variableRepository.save(var);
            }
            
        } catch (IOException ex) {
            
            Logger.getLogger(CountriesDataInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
