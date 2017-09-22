/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.services.CompanyService;

/**
 *
 * @author LukeS
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    
    @Override
    public List<Company> findOrderById(int pageSize, int pageNumber) {
        
        List<Company> companies = new ArrayList<>();
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
        
        companyRepository.findAll(request).forEach(companies::add);
        
        return companies;
    }

    @Override
    public List<Company> findOrderByCompanyName(int pageSize, int pageNumber) {
        
        List<Company> companies = new ArrayList<>();
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "companyName");
        
        companyRepository.findAll(request).forEach(companies::add);
        
        return companies;
    }
}
