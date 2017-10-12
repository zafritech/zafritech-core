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
import org.zafritech.core.data.dao.SIUnitDao;
import org.zafritech.core.data.domain.SIUnit;
import org.zafritech.core.data.repositories.SIUnitRepository;

/**
 *
 * @author LukeS
 */
@Component
public class SIUnitsDataInit {
    
    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
    
    @Autowired
    private SIUnitRepository siUnitRepository;
    
    @Transactional
    public void init() {
        
        String file = data_dir + "initialisation/si-units.json";
        
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            
            List<SIUnitDao> siUnits = Arrays.asList(mapper.readValue(new File(file), SIUnitDao[].class));
            SIUnit unit;
            
            for (SIUnitDao unitDao : siUnits) {
                
                unit = new SIUnit(unitDao.getSymbol(), unitDao.getName(), unitDao.getDefinition()); 
                siUnitRepository.save(unit);
            }
            
        } catch (IOException ex) {
            
            Logger.getLogger(LocalesDataInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
