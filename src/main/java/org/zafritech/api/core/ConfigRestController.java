/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.api.core;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.zafritech.core.data.dao.EntityTypeDao;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.repositories.EntityTypeRepository;


/**
 *
 * @author LukeS
 */
@RestController
public class ConfigRestController {
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @RequestMapping(value = "/api/configuration/entity/type/{id}", method = RequestMethod.GET)
    public ResponseEntity<EntityType> getEntityTypeById(@PathVariable(value = "id") Long id) {

        EntityType EntityType = entityTypeRepository.findOne(id); 
        
        return new ResponseEntity<EntityType>(EntityType, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/configuration/entity/keytypes/list", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getEntityTypeKeysList() {

        List<String> keys = entityTypeRepository.findDistinctKeys();
        
        return new ResponseEntity<List<String>>(keys, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/configuration/entity/type/list/{typeKey}", method = RequestMethod.GET)
    public ModelAndView listRequirementsForDocument(@PathVariable(value = "typeKey") String typeKey) {
        
        List<EntityType> types =  entityTypeRepository.findByEntityTypeKeyOrderByEntityTypeNameAsc(typeKey);
       
        ModelAndView modelView = new ModelAndView("views/admin/configuration/entity-types");
        modelView.addObject("types", types);
        modelView.addObject("typeKey", typeKey);
        
        return modelView;
    }
    
    @RequestMapping(value = "/api/configuration/entitytype/new/save", method = RequestMethod.POST)
    public ResponseEntity<EntityType> createNewEntityType(@RequestBody EntityTypeDao dao) {
        
        EntityType entity = entityTypeRepository.findByEntityTypeKeyAndEntityTypeCode(dao.getKey(), dao.getCode());
        
        if (entity != null) {
            
            return new ResponseEntity("Bad Request! Entity Key/Code combination already exists. ", HttpStatus.BAD_REQUEST);
            
        } else {
            
            EntityType entityType = new EntityType(dao.getKey(), dao.getName(), dao.getCode(), dao.getDescription());
            entityType = entityTypeRepository.save(entityType);
            
            return new ResponseEntity<EntityType>(entityType, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/api/configuration/entitytype/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<EntityType> updateEntityType(@PathVariable(value = "id") Long id,
                                                       @RequestBody EntityTypeDao dao) {
        
        EntityType entityType = entityTypeRepository.findOne(id);
        entityType.setEntityTypeKey(dao.getKey());
        entityType.setEntityTypeCode(dao.getCode());
        entityType.setEntityTypeName(dao.getName());
        entityType.setEntityTypeDescription(dao.getDescription());
        
        entityType = entityTypeRepository.save(entityType);
        
        return new ResponseEntity<EntityType>(entityType, HttpStatus.OK);
    }
}
