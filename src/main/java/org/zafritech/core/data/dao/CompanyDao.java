/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.dao;

import org.zafritech.core.data.domain.Contact;

/**
 *
 * @author LukeS
 */
public class CompanyDao {
     
    private Long id;
    
    private String companyCode;
    
    private String companyLogo;
    
    private String companyName;
    
    private String companyRole;
    
    private String companyRoleDescription;
    
    private String companyShortName;
    
    private String country;
    
    private Contact contact;

    public CompanyDao() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyRole() {
        return companyRole;
    }

    public void setCompanyRole(String companyRole) {
        this.companyRole = companyRole;
    }

    public String getCompanyRoleDescription() {
        return companyRoleDescription;
    }

    public void setCompanyRoleDescription(String companyRoleDescription) {
        this.companyRoleDescription = companyRoleDescription;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    
    
}
