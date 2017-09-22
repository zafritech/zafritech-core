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
public class DefinitionDao {
    
    private Long documentId;
    
    private Long itemId;
    
    private Long definitionId;
    
    private String newTerm;
    
    private String newTermDefinition;
    
    private String definitionType;

    public DefinitionDao() {
        
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(Long definitionId) {
        this.definitionId = definitionId;
    }

    public String getNewTerm() {
        return newTerm;
    }

    public void setNewTerm(String newTerm) {
        this.newTerm = newTerm;
    }

    public String getNewTermDefinition() {
        return newTermDefinition;
    }

    public void setNewTermDefinition(String newTermDefinition) {
        this.newTermDefinition = newTermDefinition;
    }

    public String getDefinitionType() {
        return definitionType;
    }

    public void setDefinitionType(String definitionType) {
        this.definitionType = definitionType;
    }
}
