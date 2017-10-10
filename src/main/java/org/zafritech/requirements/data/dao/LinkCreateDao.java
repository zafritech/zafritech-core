/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.dao;

import java.util.List;
import org.zafritech.core.data.domain.Document;
import org.zafritech.core.data.domain.EntityType;

/**
 *
 * @author LukeS
 */
public class LinkCreateDao {
    
    private Long srcDocumentId;
    
    private Long srcItemId;
    
    private Long dstItemId;
    
    private List<Document> dstDocuments;
    
    private List<EntityType> linkTypes;

    public LinkCreateDao() {
        
    }

    public Long getSrcDocumentId() {
        return srcDocumentId;
    }

    public void setSrcDocumentId(Long srcDocumentId) {
        this.srcDocumentId = srcDocumentId;
    }

    public Long getSrcItemId() {
        return srcItemId;
    }

    public void setSrcItemId(Long srcItemId) {
        this.srcItemId = srcItemId;
    }

    public Long getDstItemId() {
        return dstItemId;
    }

    public void setDstItemId(Long dstItemId) {
        this.dstItemId = dstItemId;
    }

    public List<Document> getDstDocuments() {
        return dstDocuments;
    }

    public void setDstDocuments(List<Document> dstDocuments) {
        this.dstDocuments = dstDocuments;
    }

    public List<EntityType> getLinkTypes() {
        return linkTypes;
    }

    public void setLinkTypes(List<EntityType> linkTypes) {
        this.linkTypes = linkTypes;
    }

}
