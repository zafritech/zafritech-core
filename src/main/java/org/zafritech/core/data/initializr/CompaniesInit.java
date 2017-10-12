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
import org.zafritech.core.data.dao.CompanyDao;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.domain.Contact;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.data.repositories.ContactRepository;
import org.zafritech.core.data.repositories.CountryRepository;
import org.zafritech.core.enums.CompanyRole;

/**
 *
 * @author LukeS
 */
@Component
public class CompaniesInit {
 
    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
    
    @Autowired
    private ContactRepository contactRepository;
     
    @Autowired
    private CompanyRepository companyRepository;
     
    @Autowired
    private CountryRepository countryRepository;
    
    @Transactional
    public void init() {
        
        String file = data_dir + "initialisation/companies.json";
        
        ObjectMapper mapper = new ObjectMapper();
         
        try {
            
            List<CompanyDao> jsonCos = Arrays.asList(mapper.readValue(new File(file), CompanyDao[].class));
            
            for (CompanyDao jsonCo : jsonCos) {
                
                Contact contact = new Contact(
                        
                        jsonCo.getContact().getEmail(), 
                        jsonCo.getContact().getFirstName(), 
                        jsonCo.getContact().getLastName(), 
                        jsonCo.getContact().getAddress(), 
                        jsonCo.getContact().getCity(), 
                        jsonCo.getContact().getState(), 
                        countryRepository.findByIso3(jsonCo.getCountry()), 
                        jsonCo.getContact().getPostCode(), 
                        jsonCo.getContact().getPhone(), 
                        jsonCo.getContact().getMobile(), 
                        jsonCo.getContact().getWebsite()
                );
                
                contact = contactRepository.save(contact);
                                
                Company company = new Company(
                
                        jsonCo.getCompanyName(), 
                        jsonCo.getCompanyShortName(), 
                        CompanyRole.valueOf(jsonCo.getCompanyRole()), 
                        jsonCo.getCompanyRoleDescription(), 
                        contact
                );
                
                company = companyRepository.save(company);
                
                String msg = "Company data initialisation: " + company.toString();
                Logger.getLogger(CompaniesInit.class.getName()).log(Level.INFO, msg);
            }
            
        } catch (IOException ex) {
            
            Logger.getLogger(CompaniesInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
