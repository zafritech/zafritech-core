/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import java.util.List;
import org.zafritech.core.data.dao.BaseLineDao;
import org.zafritech.core.data.dao.DefinitionDao;
import org.zafritech.core.data.dao.DocDao;
import org.zafritech.core.data.dao.DocEditDao;
import org.zafritech.core.data.domain.Definition;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.domain.User;

/**
 *
 * @author LukeS
 */
public interface DocumentService {
    
    Document saveDao(DocDao docDao);
    
    Document saveEditDao(DocEditDao docEditDao);
            
    Document duplicate(Long id); 
    
    void initNewProjectDocuments(Project project, User user);
    
    void initDocumentSystemVariables(Project project);
    
    List<Definition> addDefinition(DefinitionDao dao);
    
    Document createBaseLine(BaseLineDao dao);
}
