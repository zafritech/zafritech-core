/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.DocumentReference;
import org.zafritech.core.data.repositories.DocumentReferenceRepository;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.enums.ReferenceTypes;
import org.zafritech.requirements.data.dao.ItemDao;
import org.zafritech.requirements.data.dao.ItemRefDao;
import org.zafritech.requirements.data.dao.ItemTreeDao;
import org.zafritech.requirements.data.domain.Item;
import org.zafritech.requirements.data.domain.ItemType;
import org.zafritech.requirements.data.repositories.ItemRepository;
import org.zafritech.requirements.data.repositories.ItemTypeRepository;
import org.zafritech.requirements.enums.ItemClass;
import org.zafritech.requirements.services.ItemService;

/**
 *
 * @author LukeS
 */
@RestController
public class ItemRestController {
    
    @Autowired
    private DocumentRepository documentRepository;
     
    @Autowired
    private DocumentReferenceRepository docReferenceRepository;
     
    @Autowired
    private ItemTypeRepository itemTypeRepository;
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private ItemService itemService;
  
    @RequestMapping(value = "/api/requirements/document/items/item/{id}", method = RequestMethod.GET)
    public Item getItemById(@PathVariable(value = "id") Long itemId) {

        Item item = itemRepository.findOne(itemId);
        
        return item;
    }

    @RequestMapping(value = "/api/requirements/document/requirements/list/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getRequirementsByDocument(@PathVariable(value = "id") Long id) {

        List<Item> requirements = itemRepository.findByDocumentAndItemClass(documentRepository.findOne(id), ItemClass.REQUIREMENT.name());
        
        return new ResponseEntity<List<Item>>(requirements, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/requirements/document/items/list/view/{id}/{sectionId}", method = RequestMethod.GET)
    public ModelAndView listRequirementsForDocument(@PathVariable(value = "id") Long id,
                                                    @PathVariable(value = "sectionId") Long sectionId) {

        List<Item> items;
        Document document = documentRepository.findOne(id);
        
        if (sectionId > 0) {
            
            Item section = itemRepository.findOne(sectionId);
            items = itemService.fetchDocumentItemsForSection(document, section);
            
        } else {
        
            items = itemService.fetchDocumentItems(document); 
        }
        
        List<DocumentReference> applicable = docReferenceRepository.findByDocumentAndReferenceTypeOrderByReferenceRefNumberAsc(document, ReferenceTypes.REFERENCE_APPLICABLE);
        List<DocumentReference> other = docReferenceRepository.findByDocumentAndReferenceTypeOrderByReferenceRefNumberAsc(document, ReferenceTypes.REFERENCE_OTHER);
        
        ModelAndView modelView = new ModelAndView("views/requirements/document-items-fragmant");
        modelView.addObject("document", document);
        modelView.addObject("items", items);
        modelView.addObject("applicables", applicable);
        modelView.addObject("others", other);
        
        return modelView;
    }
    
    @RequestMapping(value = "/api/requirements/document/item/types/list", method = RequestMethod.GET)
    public ResponseEntity<List<ItemType>> getItemTypes() {

        List<ItemType> itemTypes = itemTypeRepository.findAllByOrderByItemTypeLongName();
        
        return new ResponseEntity<List<ItemType>>(itemTypes, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/requirements/document/items/newitem/ref/dao/{id}", method = RequestMethod.GET)
    public ItemRefDao createFirstDocumentItem(@PathVariable(value = "id") Long documentId) {

        ItemRefDao createDao = itemService.getDaoForItemCreation(documentId);
        
        return createDao;
    }

    @RequestMapping(value = "/api/requirements/document/items/identifier/next", method = RequestMethod.GET)
    public ResponseEntity<String> getNextItemIentifier(@RequestParam(value = "id", required = true) Long id,
                                                       @RequestParam(value = "template", required = true) String template) {

        String reqIdentifier = itemService.getNextRequirementIdentifier(id, template);

        return new ResponseEntity<String>(reqIdentifier, HttpStatus.OK);
    }
 
    @RequestMapping(value = "/api/requirements/document/items/item/save", method = RequestMethod.POST)
    public ResponseEntity<Item> newRquirementsSaveItem(@RequestBody ItemDao itemDao) {
        
        Item item = itemService.saveRquirementItem(itemDao);

        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/requirements/document/items/edit/save", method = RequestMethod.POST)
    public ResponseEntity<Item> saveEditedItem(@RequestBody ItemDao itemDao) {
        
        Item item = itemService.saveEditedItemDao(itemDao);

        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/requirements/document/items/item/move", method = RequestMethod.GET)
    public ResponseEntity<Item> moveItem(@RequestParam(value = "id", required = true) Long id,
                                         @RequestParam(value = "direction", required = true) String direction) {
        
        itemService.moveItemUpOrDown(id, direction); 
        Item item = itemRepository.findOne(id);
        
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/requirements/document/items/item/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Item> deleteItem(@PathVariable(value = "id") Long id) {

        Item item = itemRepository.findOne(id);
        List<Item> children = itemRepository.findByParentOrderBySortIndexAsc(item);
        
        if (children.isEmpty()) {
        
            Item parent = item.getParent();
            itemRepository.delete(item);
            
            if (parent != null) {
                
                itemService.reNumberChildItems(parent); 
            }
            
        } else {
            
            return new ResponseEntity<Item>(item, HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/requirements/document/tree/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ItemTreeDao>> getDocumentItemTree(@PathVariable(value = "id") Long id) {
        
        Document document = documentRepository.findOne(id);
        List<ItemTreeDao> headersTree = itemService.getTableOfContents(document); 
        
        return new ResponseEntity<List<ItemTreeDao>>(headersTree, HttpStatus.OK);
    }
}
