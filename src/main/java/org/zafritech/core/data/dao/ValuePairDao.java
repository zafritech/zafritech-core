/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.dao;

/**
 *
 * @author LukeS
 */
public class ValuePairDao {
 
    private Long Id;
    
    private String itemName;

    public ValuePairDao() {
        
    }

    public ValuePairDao(Long Id, String Name) {
        
        this.Id = Id;
        this.itemName = Name;
    }

    @Override
    public String toString() {
        
        return "ValuePairDao{" + "Id=" + Id + ", itemName=" + itemName + '}';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
