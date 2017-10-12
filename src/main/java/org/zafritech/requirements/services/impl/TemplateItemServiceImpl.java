/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.requirements.data.dao.TemplateDao;
import org.zafritech.requirements.data.domain.Item;
import org.zafritech.requirements.data.domain.Template;
import org.zafritech.requirements.data.domain.TemplateItem;
import org.zafritech.requirements.data.repositories.ItemRepository;
import org.zafritech.requirements.data.repositories.TemplateItemRepository;
import org.zafritech.requirements.data.repositories.TemplateRepository;
import org.zafritech.requirements.services.TemplateItemService;

/**
 *
 * @author LukeS
 */
@Service
public class TemplateItemServiceImpl implements TemplateItemService {

    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    
    @Autowired
    private TemplateRepository templateRepository;
   
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private TemplateItemRepository templateItemRepository;
            
    @Override
    public Template createTemplateFromDocument(TemplateDao dao) {
        
        Document document = documentRepository.findOne(dao.getDocumentId());
        
        Template template = templateRepository.save(new Template(dao.getTemplateName(), 
                                                                 dao.getTemplateLongName(),
                                                                 dao.getTemplateDescription(),
                                                                 document.getContentDescriptor(),
                                                                 entityTypeRepository.findOne(dao.getDocumentTypeId())));
        
        return addTemplateItems(document, template);
    }

    private Template addTemplateItems(Document document, Template template) {
        
        List<Item> firstLevelItems = itemRepository.findByDocumentAndItemLevelOrderBySortIndexAsc(document, 1);
        
        for(Item item : firstLevelItems) {
            
            TemplateItem templateItem = saveTemplateItem(item, template, null);
            getItemChildren(item, template, templateItem);
        }
        
        return template;
    }
    
    private List<Item> getItemChildren(Item item, Template template, TemplateItem templateItem) {
        
        List<Item> childItems = itemRepository.findByParentOrderBySortIndexAsc(item); 
        
        for (Item child : childItems) {

            TemplateItem childTemplateItem = saveTemplateItem(child, template, templateItem);
            getItemChildren(child, template, childTemplateItem);
        }
        
        return childItems;
    }
    
    private TemplateItem saveTemplateItem(Item item, Template template, TemplateItem parent) {
        
        String systemId;
        
        TemplateItem lastItem = templateItemRepository.findFirstByTemplateOrderBySystemIdDesc(template);
        
        if (lastItem != null) {
            
            systemId = String.format("%05d", Integer.valueOf(lastItem.getSystemId().replaceAll("[^0-9]", "")) + 1);

        } else {
            
            systemId = String.format("%05d", 1);
        }
       
        TemplateItem templateItem = new TemplateItem("ID-TEMPLATE-" + systemId,
                                                     item.getItemClass(),
                                                     item.getItemLevel(),
                                                     item.getItemNumber(),
                                                     item.getItemValue(),
                                                     item.getItemType(),
                                                     item.getMediaType(),
                                                     parent != null ? parent.getSystemId() : null,
                                                     template,
                                                     item.getSortIndex());
        
        templateItem = templateItemRepository.save(templateItem);
        
        return templateItem;
    }
}
