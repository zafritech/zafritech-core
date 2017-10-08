/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.DocumentReference;
import org.zafritech.core.enums.ReferenceTypes;

/**
 *
 * @author LukeS
 */
public interface DocumentReferenceRepository extends CrudRepository<DocumentReference, Long> {
    
    DocumentReference findFirstByDocumentOrderByDocRefIdDesc(Document document);
    
    List<DocumentReference> findByDocumentAndReferenceType(Document document, ReferenceTypes type);
    
    List<DocumentReference> findByDocumentAndReferenceTypeOrderByReferenceRefTitleAsc(Document document, ReferenceTypes type);
}
