/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.core.data.dao.DefinitionDao;
import org.zafritech.core.data.domain.Definition;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.Locale;
import org.zafritech.core.data.repositories.DefinitionRepository;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.LocaleRepository;
import org.zafritech.core.enums.DefinitionTypes;

/**
 *
 * @author LukeS
 */
@RestController
public class DefinitionsRestController {
    
    @Autowired
    private LocaleRepository localeRepository;
    
    @Autowired
    private DefinitionRepository definitionRepository;
          
    @Autowired
    private DocumentRepository documentRepository;
     
    @RequestMapping(value = "/api/definitions/abbreviation/list", method = GET)
    public ResponseEntity<List<Definition>> listAbbreviations() {
        
        List<Definition> abbreviations = definitionRepository.findByDefinitionTypeOrderByTerm(DefinitionTypes.ABBREVIATION); 
        
        return new ResponseEntity<List<Definition>>(abbreviations, HttpStatus.OK);
    }
     
    @RequestMapping(value = "/api/definitions/acronym/list", method = GET)
    public ResponseEntity<List<Definition>> listAcronyms() {
        
        List<Definition> acronyms = definitionRepository.findByDefinitionTypeOrderByTerm(DefinitionTypes.ACRONYM); 
        
        return new ResponseEntity<List<Definition>>(acronyms, HttpStatus.OK);
    }
     
    @RequestMapping(value = "/api/definitions/definition/list", method = GET)
    public ResponseEntity<List<Definition>> listDefinitions() {
        
        List<Definition> definitions = definitionRepository.findByDefinitionTypeOrderByTerm(DefinitionTypes.DEFINITION); 
        
        return new ResponseEntity<List<Definition>>(definitions, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/definitions/definition/{id}", method = GET)
    public ResponseEntity<Definition> getAbbreviation(@PathVariable(value = "id") Long id) {
        
        Definition abbreviation = definitionRepository.findOne(id); 
        
        return new ResponseEntity<Definition>(abbreviation, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/requirements/document/definition/add", method = RequestMethod.POST)
    public ResponseEntity<List<Definition>> getDocumentAddAbbreviation(@RequestBody DefinitionDao dao) {
        
        Definition abbrev;
        Locale language = localeRepository.findByCode("en_GB");
        
        Document document = documentRepository.findOne(dao.getDocumentId());
        
        if (!dao.getNewTerm().isEmpty()) {
            
            abbrev = new Definition(dao.getNewTerm(), 
                                    dao.getNewTermDefinition(), 
                                    DefinitionTypes.valueOf(dao.getDefinitionType()), 
                                    language); 
            
            abbrev = definitionRepository.save(abbrev);
            
        } else {
            
            abbrev = definitionRepository.findOne(dao.getDefinitionId());
        }
        
        List<Definition> definitions = document.getDefinitions();
        definitions.add(abbrev);
        
        System.out.println(definitions);
        
        document.setDefinitions(new ArrayList(definitions)); 
        documentRepository.save(document);
        
        return new ResponseEntity<List<Definition>>(definitions, HttpStatus.OK);
    }
}
