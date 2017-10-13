/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.api.core;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.core.data.domain.Country;
import org.zafritech.core.data.repositories.CountryRepository;


/**
 *
 * @author LukeS
 */
@RestController
public class ContactsRestController {
    
    @Autowired
    private CountryRepository countryRepository;
 
    @RequestMapping(value = "/api/contacts/countries/list", method = GET)
    public ResponseEntity<List<Country>> getCountriesList(Model model) {
        
        List<Country> countries = countryRepository.findAllByOrderByCountryNameAsc();
                 
        return new ResponseEntity<List<Country>>(countries, HttpStatus.OK);
    }
}
