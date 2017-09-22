/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.zafritech.core.data.domain.Country;

/**
 *
 * @author LukeS
 */
public interface CountryRepository extends PagingAndSortingRepository<Country, Long> {
    
    Country findByIso2(String iso2);
    
    Country findByIso3(String iso3);
    
    List<Country> findAllByOrderByCountryNameAsc();
}
