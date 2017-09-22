/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.Folder;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.projections.DocumentView;

/**
 *
 * @author LukeS
 */
public interface DocumentRepository extends PagingAndSortingRepository<Document, Long> {
    
    Document findByUuId(String uuid);
    
    @Override
    Page<Document> findAll(Pageable pageable);
    
    Page<Document> findAllByOwner(Pageable pageable, User user);
     
    List<Document> findByOwner(User user);
     
    List<Document> findByOwnerOrderByModifiedDateDesc(User user);
    
    List<Document> findByProject(Project project);
    
    List<Document> findByProjectOrderByDocumentNameAsc(Project project);
    
    List<Document> findByFolder(Folder folder);
    
    // Projection Views
    List<DocumentView> findDocumentViewByOwner(User user);

    List<DocumentView> findDocumentViewByOwnerOrderByModifiedDateDesc(User user);
}