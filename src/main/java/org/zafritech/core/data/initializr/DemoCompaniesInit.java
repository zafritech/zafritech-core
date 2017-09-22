/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.initializr;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.dao.DemoCoDao;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.data.repositories.CompanyRepository;
import org.zafritech.core.enums.CompanyRole;

/**
 *
 * @author LukeS
 */
@Component
public class DemoCompaniesInit {
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Transactional
    public void init(List<DemoCoDao> coDaos) {
        
        for (DemoCoDao dao : coDaos) {

            String name = dao.getCompanyName();
            String nameArray[] = name.split(" ", 2);

            Company company = new Company(name, nameArray[0]);
            company.setCompanyRole(CompanyRole.THIRD_PARTY_ORGANISATION); 
            companyRepository.save(company);
        }
    }
}
