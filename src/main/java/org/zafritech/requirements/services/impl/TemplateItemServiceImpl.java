/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.core.services.UserService;
import org.zafritech.requirements.data.dao.TemplateDao;
import org.zafritech.requirements.data.dao.TemplateItemToJsonDao;
import org.zafritech.requirements.data.dao.TemplateToJsonDao;
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

    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
    
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
    
    @Autowired
    private UserService userService;
            
    @Override
    public Template createTemplateFromDocument(TemplateDao dao) {
        
        Document document = documentRepository.findOne(dao.getDocumentId());
        
        Template template = templateRepository.save(new Template(dao.getTemplateName(), 
                                                                 dao.getTemplateLongName(),
                                                                 dao.getTemplateDescription(),
                                                                 document.getContentDescriptor(),
                                                                 entityTypeRepository.findOne(dao.getDocumentTypeId()),
                                                                 userService.loggedInUser())
        );
        
        return addDocumentItemsToTemplate(document, template);
    }
    
    @Override
    public void createFileFromTemplate(Template template, String templateFormat) {
        
        String templateDirectory = data_dir + "templates";
        DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
        
        List<TemplateItemToJsonDao> jsomItems = new ArrayList<>();
        
        List<TemplateItem> templateItems = templateItemRepository.findByTemplateOrderBySystemIdAsc(template);
        
        for (TemplateItem templateItem : templateItems) {
            
            TemplateItemToJsonDao jsonItem = new TemplateItemToJsonDao(templateItem.getSystemId(),
                                                                       templateItem.getItemClass(),
                                                                       templateItem.getItemNumber() != null ? templateItem.getItemNumber() : "",
                                                                       templateItem.getItemValue(),
                                                                       templateItem.getItemType() != null ? templateItem.getItemType().getEntityTypeCode() : "",
                                                                       templateItem.getMediaType().name(),
                                                                       templateItem.getParentSysId() != null ? templateItem.getParentSysId() : "",
                                                                       templateItem.getItemLevel(),
                                                                       templateItem.getSortIndex());
            
            jsomItems.add(jsonItem);
        }
                
        TemplateToJsonDao dao = new TemplateToJsonDao(template.getTemplateName(),
                                                      template.getTemplateLongName(),
                                                      template.getTemplateDescription(),
                                                      template.getContentDescriptor().getDescriptorCode(),
                                                      template.getDocumentType().getEntityTypeCode(),
                                                      jsomItems);
        
        try {
        
            String timeStamp = timeFormat.format(System.currentTimeMillis());
            ObjectMapper mapper;

            if (templateFormat.equalsIgnoreCase("XML")) {
                
                mapper= new XmlMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(new File(templateDirectory + File.separator + template.getDocumentType().getEntityTypeCode() + "_" + timeStamp + "_template.xml"), dao);

            } else if (templateFormat.equalsIgnoreCase("JSON")) {

                mapper= new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(new File(templateDirectory + File.separator + template.getDocumentType().getEntityTypeCode() + "_" + timeStamp + "_template.json"), dao);
            }
            
        } catch (IOException ex) {
            
            Logger.getLogger(TemplateItemServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Template addDocumentItemsToTemplate(Document document, Template template) {
        
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
