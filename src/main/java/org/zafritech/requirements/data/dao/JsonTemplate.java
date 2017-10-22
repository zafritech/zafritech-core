/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.dao;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author LukeS
 */
@JsonPropertyOrder({"name", "longName", "type", "content", "description"})
@JacksonXmlRootElement(localName = "template")
public class JsonTemplate {
    
    @JacksonXmlProperty(localName="id", isAttribute=true)
    private String uuId;
    
    private String name;
    
    private String longName;

    private String description;
    
    private String content;
    
    private String type;
    
    private List<JsonTemplateItem> items;

    public JsonTemplate() {
        
        this.uuId = UUID.randomUUID().toString().toUpperCase();
    }

    public JsonTemplate(String name, 
                        String longName, 
                        String description, 
                        String content, 
                        String type, 
                        List<JsonTemplateItem> items) {
        
        this.uuId = UUID.randomUUID().toString().toUpperCase();
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

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
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

    public List<JsonTemplateItem> getItems() {
        return items;
    }

    public void setItems(List<JsonTemplateItem> items) {
        this.items = items;
    }
}
