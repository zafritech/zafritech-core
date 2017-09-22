/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.converters;

import java.util.Arrays;
import org.springframework.core.convert.converter.Converter;
import org.zafritech.requirements.data.dao.ItemRefDao;
import org.zafritech.requirements.data.domain.Item;
import org.zafritech.requirements.enums.ItemClass;
import org.zafritech.requirements.enums.MediaType;

/**
 *
 * @author LukeS
 */
public class DaoToRefItemConverter implements Converter<Item, ItemRefDao> {

    @Override
    public ItemRefDao convert(Item item) {

        ItemRefDao createDao = new ItemRefDao(item);
        
        createDao.setItemLevels(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); 
        createDao.setItemClasses(Arrays.asList(ItemClass.values())); 
        createDao.setMediaTypes(Arrays.asList(MediaType.values())); 
        
        // List<MediaType> mediaTypes and List<SystemVariable> identPrefices 
        // will be added by the ItemService. For some reason JPA doesn't work 
        // in this class -> returns null Exceptions.
        
        return createDao;
    }
}
