/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.services.InitService;

/**
 *
 * @author LukeS
 */
@RestController
public class CompanyRestController {
    
    @Autowired
    private CompanyRepository companyRepository;
  
    @RequestMapping("/api/admin/companies/list")
    public ResponseEntity<List<Company>> getCompaniesList(Model model) {
        
        List<Company> companies = companyRepository.findAllByOrderByCompanyNameAsc();
        
        return new ResponseEntity<List<Company>>(companies, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/admin/companies/contacts/{uuid}", method = GET)
    public ResponseEntity<Company> getCompanyByUuId(@PathVariable(value = "uuid") String uuid) {
        
        Company company = companyRepository.getByUuId(uuid); 
        
        return new ResponseEntity<Company>(company, HttpStatus.OK);
    }
}