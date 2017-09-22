/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.ContactDao;
import org.zafritech.core.data.dao.CountryDao;
import org.zafritech.core.data.domain.Contact;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.repositories.ContactRepository;
import org.zafritech.core.data.repositories.CountryRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.services.ContactService;

/**
 *
 * @author LukeS
 */
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ContactRepository contactRepository;
    
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Override
    public Integer updateCountryData(List<CountryDao> daos) {
        
        return null;
    }

    @Override
    public Contact getUserContactFromDao(User user, ContactDao dao) {

        Contact contact = user.getContact(); 
        
        if (contact == null) {
            
            contact = new Contact(user.getEmail(), countryRepository.findByIso3(dao.getCountry())); 
        }
        
        contact.setFirstName(user.getFirstName());
        contact.setLastName(user.getLastName());
        contact.setAddress(dao.getAddress());
        contact.setPhone(dao.getPhone());
        contact.setMobile(dao.getMobile());
        contact.setWebsite(dao.getWebsite());
        contact.setCountry(countryRepository.findByIso3(dao.getCountry()));
        contact.setState(dao.getState());
        contact.setCity(dao.getCity());
        contact.setPostCode(dao.getPostCode()); 
        contact = contactRepository.save(contact);
        
        user.setContact(contact);
        userRepository.save(user);
        
        return contact;
    }
}
