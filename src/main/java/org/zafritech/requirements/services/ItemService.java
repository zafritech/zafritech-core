/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zafritech.core.data.domain.Document;
import org.zafritech.requirements.data.dao.ItemDao;
import org.zafritech.requirements.data.dao.ItemRefDao;
import org.zafritech.requirements.data.dao.ItemTreeDao;
import org.zafritech.requirements.data.domain.Item;
import org.zafritech.requirements.data.domain.Link;
import org.zafritech.requirements.data.domain.Template;

/**
 *
 * @author LukeS
 */
@Service
public interface ItemService {
    
    ItemRefDao getDaoForItemCreation(Long id);
            
    void updateItemHistory(Item item);
    
    void updateLinksChanged(Item item);
    
    void resetLinkChanged(Item item, Link link);

    int incrCommentCount(Long id);
    
    String getNextSystemIdentifier(Long id);

    String getNextRequirementIdentifier(Long id, String template);
    
    Item saveItem(Item item);
    
    Item saveImageItem(MultipartFile upLoadedFile, Long documentId, Long parentId, String imageCaption, Integer itemLevel);
    
    Item saveRquirementItem(ItemDao itemDao);
    
    Item saveEditedItemDao(ItemDao itemDao);
    
    void moveItemUpOrDown(Long id, String direction);
    
    void reNumberChildItems(Item parent);
    
    List<Item> fetchDocumentItems(Document document);
    
    List<Item> fetchDocumentItemsForSection(Document document, Item section);
    
    List<ItemTreeDao> getTableOfContents(Document document);
    
    List<ItemTreeDao> getOpenDocumentTitlesTreeAll();
    
    List<ItemTreeDao> getOpenDocumentTitlesTreeExcluding(Document document);
    
    Document importTemplateToDocument(Document document, Template template);
    
    Document baseLineRequirementsItems(Document document);
}
