/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.converters;

import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.zafritech.core.data.domain.EntityType;
import org.zafritech.core.data.repositories.DocumentRepository;
import org.zafritech.core.data.repositories.EntityTypeRepository;
import org.zafritech.requirements.data.dao.ItemDao;
import org.zafritech.requirements.data.domain.Item;
import org.zafritech.requirements.data.repositories.ItemRepository;
import org.zafritech.requirements.enums.MediaType;

/**
 *
 * @author LukeS
 */
@Component
public class DaoToItemConverter implements Converter<ItemDao, Item> {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EntityTypeRepository entityTypeRepository;

    @Override
    public Item convert(ItemDao itemDao) {

        if (itemDao.getId() != null) {

            EntityType itemType = entityTypeRepository.findOne(itemDao.getItemTypeId());

            Item item = itemRepository.findOne(itemDao.getId());

            item.setSystemId(itemDao.getSystemId());
            item.setItemType(itemType);
            item.setIdentifier(itemDao.getIdentifier());
            item.setItemNumber(itemDao.getItemNumber());
            item.setItemValue(itemDao.getItemValue());
            item.setItemClass(itemDao.getItemClass());
            item.setItemLevel(itemDao.getItemLevel());
            item.setItemVersion(itemDao.getItemVersion());
            item.setDocument(documentRepository.findOne(itemDao.getDocumentId()));
            item.setModifiedDate(new Timestamp(System.currentTimeMillis()));

            return item;

        } else {

            EntityType itemType;

            if (itemDao.getItemTypeId() != null) {

                itemType = entityTypeRepository.findOne(itemDao.getItemTypeId());

            } else {

                itemType = null;
            }

            Item item = new Item(itemDao.getSystemId(),     // To be fixed
                    itemDao.getIdentifier(),                // To be fixed
                    itemDao.getItemValue(),
                    itemType,
                    MediaType.TEXT,
                    documentRepository.findOne(itemDao.getDocumentId()),
                    itemDao.getParentId() != null ? itemRepository.findOne(itemDao.getParentId()) : null);
            
            item.setItemNumber(itemDao.getItemNumber());
            item.setItemClass(itemDao.getItemClass());
            item.setItemLevel(itemDao.getItemLevel());
            item.setSortIndex(itemDao.getSortIndex());

            return item;
        }
    }
}
