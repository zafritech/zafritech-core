/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.dao;

/**
 *
 * @author LukeS
 */
public class DummyItemDao {

    private Long documentId;
    
    private Long parentId;
    
    private Long itemTypeId;
    
    private Integer itemCount;
    
    private Integer itemLevel;
    
    private Integer maxWords;
    
    private Integer minWords;

    public DummyItemDao() { 
        
    }

    @Override
    public String toString() {
        
        return "DummyItemDao{" + "documentId=" + getDocumentId() 
                + ", parentId=" + getParentId() + ", itemTypeId=" 
                + getItemTypeId() + ", itemCount=" + getItemCount() 
                + ", itemLevel=" + getItemLevel() + ", maxWords=" 
                + getMaxWords() + ", minWords=" + getMinWords() + '}';
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(Integer itemLevel) {
        this.itemLevel = itemLevel;
    }

    public Integer getMaxWords() {
        return maxWords;
    }

    public void setMaxWords(Integer maxWords) {
        this.maxWords = maxWords;
    }

    public Integer getMinWords() {
        return minWords;
    }

    public void setMinWords(Integer minWords) {
        this.minWords = minWords;
    }
}
