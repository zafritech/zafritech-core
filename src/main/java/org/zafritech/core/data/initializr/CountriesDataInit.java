/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.dao.CountryDao;
import org.zafritech.core.data.domain.Country;
import org.zafritech.core.data.repositories.CountryRepository;

/**
 *
 * @author LukeS
 */
@Component
public class CountriesDataInit {
    
    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
    
    @Value("${zafritech.paths.images-dir}")
    private String images_dir;
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Transactional
    public void init() {
        
        String dataFile = data_dir + "initialisation/countries.json";
        String flagPath = images_dir + "/flags/";
        
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            
            List<CountryDao> jsonCountries = Arrays.asList(mapper.readValue(new File(dataFile), CountryDao[].class));
            
            for (CountryDao dao : jsonCountries) {
                
                Country country = new Country(dao.getCountryName(), 
                                                 dao.getIso2(), 
                                                 dao.getIso3(), 
                                                 dao.getIsoNum(), 
                                                 dao.getTld(), 
                                                 dao.getDialCode(), 
                                                 dao.getCapital(),
                                                 dao.getFlag());
                
                country = countryRepository.save(country);
                
                URL url = new URL(country.getFlag());
                File file = new File(flagPath + FilenameUtils.getName(url.getPath()));
                
                if (!file.exists()) {
                
                    FileUtils.copyURLToFile(url, file, 10000, 10000);
                }
                
                String msg = "Country data initialisation: " + country.toString();
                Logger.getLogger(CountriesDataInit.class.getName()).log(Level.INFO, msg);
            }
            
        } catch (IOException ex) {
            
            Logger.getLogger(CountriesDataInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
