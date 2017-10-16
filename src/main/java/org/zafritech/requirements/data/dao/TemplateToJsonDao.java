/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.dao;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

/**
 *
 * @author LukeS
 */
@JsonPropertyOrder({"name", "longName", "type", "content", "description"})
@JacksonXmlRootElement(localName = "Template")
public class TemplateToJsonDao {
    
    private String name;
    
    private String longName;

    private String description;
    
    private String content;
    
    private String type;
    
    private List<TemplateItemToJsonDao> items;

    public TemplateToJsonDao() {
        
    }

    public TemplateToJsonDao(String name, 
                             String longName, 
                             String description, 
                             String content, 
                             String type, 
                             List<TemplateItemToJsonDao> items) {
        
        this.name = name;
        this.longName = longName;
        this.description = description;
        this.content = content;
        this.type = type;
        this.items = items;
    }

    @Override
    public String toString() {
        
        return "TemplateToJsonDao{" + "name=" + getName() + ", longName=" 
                + getLongName() + ", description=" + getDescription() 
                + ", content=" + getContent() + ", type=" + getType() 
                + ", items=" + getItems() + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TemplateItemToJsonDao> getItems() {
        return items;
    }

    public void setItems(List<TemplateItemToJsonDao> items) {
        this.items = items;
    }
}
