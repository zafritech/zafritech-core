/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.Document;
import org.zafritech.requirements.data.domain.LinkGroup;

/**
 *
 * @author LukeS
 */
public interface LinkGroupRepository extends CrudRepository<LinkGroup, Long> {
    
    LinkGroup findFirstBySourceDocumentAndDestinationDocument(Document source, Document destination);
}
