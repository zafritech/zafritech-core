/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.requirements.data.domain.Template;

/**
 *
 * @author LukeS
 */
public interface TemplateRepository extends CrudRepository<Template, Long> {
    
    List<Template> findByDocumentTypeOrderByTemplateName(EntityType documentType);
}
