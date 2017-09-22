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
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.Locale;
import org.zafritech.core.data.repositories.LocaleRepository;

/**
 *
 * @author LukeS
 */
@Component
public class LocalesDataInit {
    
    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
    
    @Autowired
    private LocaleRepository localeRepository;
    
    @Transactional
    public void init() {
        
        String file = data_dir + "json/locales.json";
        
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            
            List<JSONObject> jsonObjects = Arrays.asList(mapper.readValue(new File(file), JSONObject.class));
            JSONObject locales = jsonObjects.get(0); 
            
            for (Object key : locales.keySet()) {
                
                String keyStr = (String)key;
                Object keyValue = locales.get(keyStr);
                
                Locale locale = new Locale(keyStr, keyValue.toString());
                localeRepository.save(locale);
            }
            
        } catch (IOException ex) {
            
            Logger.getLogger(LocalesDataInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
