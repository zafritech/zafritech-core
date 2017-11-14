/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.repositories;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.Document;
import org.zafritech.requirements.data.domain.Item;
import org.zafritech.requirements.data.domain.ItemCategory;
import org.zafritech.requirements.enums.ItemStatus;

/**
 *
 * @author LukeS
 */
public interface ItemRepository extends CrudRepository<Item, Long> {
    
    Item findBySystemId(String sysId);
    
    Item findFirstByIdentifier(String ident);

    List<Item> findByDocumentId(Long id);

    List<Item> findByDocumentIdOrderByIdDesc(Long id);

    Item findFirstByOrderBySystemIdDesc();

    Item findFirstByIdentifierContainingOrderByIdentifierDesc(String identTemplate);

    List<Item> findByDocumentIdOrderBySortIndexAsc(Long id);
    
    List<Item> findByDocument(Pageable pageable, Document artifact);
    
    List<Item> findByItemCategory(ItemCategory category);
    
    List<Item> findByItemCategory(Pageable pageable, ItemCategory category);
    
    List<Item> findByItemCategoryAndItemStatus(ItemCategory category, ItemStatus itemStatus);
    
    List<Item> findByDocumentAndItemClass(Document document, String itemClass);
    
    List<Item> findByParentOrderBySortIndexAsc(Item parent);
    
    List<Item> findByParentOrderBySortIndexDesc(Item parent);
    
    List<Item> findByParentAndItemClassOrderBySortIndexAsc(Item parent, String itemClass);
    
    Item findFirstByParentOrderBySortIndexDesc(Item parent);
    
    List<Item> findByDocumentAndItemLevelOrderBySortIndexAsc(Document document, Integer level);
    
    List<Item> findByDocumentAndItemLevelOrderBySortIndexDesc(Document document, Integer level);
    
    List<Item> findByDocumentAndItemLevelAndItemClassOrderBySortIndexAsc(Document document, Integer level, String itemClass);
    
    List<Item> findByDocumentAndItemClassOrderBySortIndexAsc(Document document, String itemClass);
    
    Item findFirstByDocumentAndItemLevelOrderBySortIndexDesc(Document document, Integer level);
    
    Item findFirstByItemLevelAndParentOrderBySortIndexDesc(Integer level, Item parent);
    
    Item findFirstByDocumentAndSortIndex(Document document, Integer index);
    
    Item findFirstByDocumentOrderBySortIndexDesc(Document document);
}
