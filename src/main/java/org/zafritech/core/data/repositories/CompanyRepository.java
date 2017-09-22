/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.zafritech.core.data.domain.Company;
import org.zafritech.core.enums.CompanyRole;

/**
 *
 * @author LukeS
 */
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
    
    @Override
    Set<Company> findAll();
    
    @Override
    Page<Company> findAll(Pageable pageable);
    
    List<Company> findAllByOrderByCompanyNameAsc();
    
    Company getByUuId(String uuid);
    
    Company findFirstByCompanyRole(CompanyRole role);
}
