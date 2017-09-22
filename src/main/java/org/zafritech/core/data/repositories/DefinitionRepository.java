/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.Definition;
import org.zafritech.core.enums.DefinitionTypes;

/**
 *
 * @author LukeS
 */
public interface DefinitionRepository extends CrudRepository<Definition, Long> {
    
    List<Definition> findByOrderByTerm();
    
    List<Definition> findByDefinitionTypeOrderByTerm(DefinitionTypes type);
}
