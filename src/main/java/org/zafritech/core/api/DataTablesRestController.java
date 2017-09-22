/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.zafritech.core.data.dao.DocViewDao;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.services.UserService;

/**
 *
 * @author LukeS
 */
@RestController
public class DataTablesRestController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @RequestMapping(value = "/api/documents/user/documents/list/", method = GET)
    public ResponseEntity<String> getDocument() throws JsonProcessingException {
        
        User user = userService.loggedInUser();
        
        List<Document> documents = documentRepository.findByOwnerOrderByModifiedDateDesc(user); 
        List<DocViewDao> documentViews = new ArrayList<>();
            
        System.out.println("\r\n");
        
        for (Document document : documents) {
            
            DocViewDao dao = new DocViewDao();
            
            dao.setId(document.getId());
            dao.setIdentifier(document.getIdentifier());
            dao.setDocumentName(document.getDocumentName());
            dao.setProjectName(document.getProject().getProjectName());
            dao.setInfoClass(document.getInfoClass().getClassCode());
            dao.setVersion(document.getDocumentIssue()); 
            dao.setModDate(document.getModifiedDate().toString()); 
            
            documentViews.add(dao);
                    
            System.out.println(dao);
        }
        
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(documentViews);
        
        System.out.println("\r\n" + json);

        return new ResponseEntity<String>(mapper.writeValueAsString(json), HttpStatus.OK);
    }
}
