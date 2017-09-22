/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.InformationClass;

/**
 *
 * @author LukeS
 */
public interface InformationClassRepository extends CrudRepository<InformationClass, Long> {
    
    List<InformationClass> findAllByOrderByClassNameAsc();
    
    InformationClass findByClassCode(String classCode);
}
