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
public class EntityTypeDao {
    
    private String key;
    
    private String name;
    
    private String code;
    
    private String description;

    public EntityTypeDao() {
        
    }

    @Override
    public String toString() {
        
        return "EntityTypeDao{" + "key=" + key 
                + ", name=" + name + ", code=" 
                + code + ", description=" + description + '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
