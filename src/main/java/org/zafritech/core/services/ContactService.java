/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import java.util.List;
import org.zafritech.core.data.dao.ContactDao;
import org.zafritech.core.data.dao.CountryDao;
import org.zafritech.core.data.domain.Contact;
import org.zafritech.core.data.domain.User;

/**
 *
 * @author LukeS
 */
public interface ContactService {
    
    Integer updateCountryData(List<CountryDao> countries);
    
    Contact getUserContactFromDao(User user, ContactDao dao);
}
