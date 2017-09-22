/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import org.zafritech.core.data.domain.User;

/**
 *
 * @author LukeS
 */
@Projection(name = "UserView", types = User.class)
public interface UserView {
    
    String getId();

    @Value("#{target.uuId}")
    String getUuId();
    
    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.firstName} #{target.lastName}")
    String getName();
    
    @Value("#{target.mainRole}")
    String getMainRole();

    @Value("#{target.contact.address}")
    String getAddress();
    
    @Value("#{target.contact.city}")
    String getCity();
    
    @Value("#{target.contact.mobile}")
    String getMobile();
    
    @Value("#{target.contact.phone}")
    String getPhone();
    
    @Value("#{target.contact.postCode}")
    String getPostCode();
    
    @Value("#{target.contact.state}")
    String getState();
    
    @Value("#{target.contact.website}")
    String GetWebsite();
    
    @Value("#{target.contact.contry.countryName}")
    String GetCountry();
}
