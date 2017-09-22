/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.dao;

import java.util.List;
import org.zafritech.core.data.domain.SystemVariable;
import org.zafritech.requirements.data.domain.Item;
import org.zafritech.requirements.data.domain.ItemType;
import org.zafritech.requirements.enums.ItemClass;
import org.zafritech.requirements.enums.MediaType;

/**
 *
 * @author LukeS
 */
public class ItemRefDao {
    
    private Item item;
    
    private int[] itemLevels;
    
    private List<ItemClass> itemClasses;
    
    private List<ItemType> itemTypes;
    
    private List<MediaType> mediaTypes;
    
    private List<SystemVariable> identPrefices;

    public ItemRefDao() {
        
    }

    public ItemRefDao(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int[] getItemLevels() {
        return itemLevels;
    }

    public void setItemLevels(int[] itemLevels) {
        this.itemLevels = itemLevels;
    }

    public List<ItemClass> getItemClasses() {
        return itemClasses;
    }

    public void setItemClasses(List<ItemClass> itemClasses) {
        this.itemClasses = itemClasses;
    }

    public List<ItemType> getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(List<ItemType> itemTypes) {
        this.itemTypes = itemTypes;
    }

    public List<MediaType> getMediaTypes() {
        return mediaTypes;
    }

    public void setMediaTypes(List<MediaType> mediaTypes) {
        this.mediaTypes = mediaTypes;
    }

    public List<SystemVariable> getIdentPrefices() {
        return identPrefices;
    }

    public void setIdentPrefices(List<SystemVariable> identPrefices) {
        this.identPrefices = identPrefices;
    }
}
