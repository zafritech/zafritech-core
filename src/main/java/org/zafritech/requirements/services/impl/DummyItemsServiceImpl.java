/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.services.impl;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.domain.SystemVariable;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.data.repositories.SystemVariableRepository;
import org.zafritech.requirements.data.dao.DummyItemDao;
import org.zafritech.requirements.data.domain.Item;
import org.zafritech.requirements.data.repositories.ItemRepository;
import org.zafritech.requirements.enums.ItemClass;
import org.zafritech.requirements.enums.MediaType;
import org.zafritech.requirements.services.DummyItemsService;
import org.zafritech.requirements.services.ItemService;

/**
 *
 * @author LukeS
 */
@Service
public class DummyItemsServiceImpl implements DummyItemsService {

    @Autowired
    private DocumentRepository documentRepository;
     
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private SystemVariableRepository variableRepository;
    
    @Override
    public List<Item> createDummyItems(DummyItemDao dao) {
        
        List<Item> items = new ArrayList<>();
        
        Document document = documentRepository.findOne(dao.getDocumentId());
        Item parent = itemRepository.findOne(dao.getParentId());
        EntityType entityType = entityTypeRepository.findOne(dao.getItemTypeId());
        String typeCode = entityType.getEntityTypeCode();
        Integer sortIndex = 0;
        
        List<SystemVariable> variables = variableRepository.findByOwnerIdAndOwnerTypeAndVariableName(document.getId(), "DOCUMENT", "REQUIREMENT_ID_TEMPLATE");
        String identTemplate = "";
        for (SystemVariable variable : variables) {
            
            String value = variable.getVariableValue();
            
            if (value.lastIndexOf(typeCode) > -1) {
                
                identTemplate = variable.getVariableValue();
            }
        }
        
        Lorem lorem = LoremIpsum.getInstance();
        
        for(int i = 1; i <= dao.getItemCount(); i++) {
            
            String itemValue = lorem.getWords(dao.getMinWords(), dao.getMaxWords()); 
            
            Item item = new Item(itemService.getNextSystemIdentifier(document.getId()),
                                 itemService.getNextRequirementIdentifier(document.getId(), identTemplate),
                                 itemValue,
                                 entityType, 
                                 MediaType.TEXT,
                                 document,
                                 parent);
            
            item.setItemClass(ItemClass.REQUIREMENT.name());
            item.setSortIndex(sortIndex++); 
            item.setItemLevel(dao.getItemLevel()); 
            
            item = itemRepository.save(item);
            items.add(item);
        }
        
        return items;
    }
    
}
