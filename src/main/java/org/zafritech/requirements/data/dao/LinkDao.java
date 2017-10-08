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
public class LinkDao {
    
    private Long srcItemId;
    
    private Long srcDocumentId;
    
    private Long dstDocumentId;
    
    private Long dstItemId;
    
    private Long linkTypeId;
    
    private String linkComment;

    public LinkDao() {
        
    }

    public Long getSrcItemId() {
        return srcItemId;
    }

    public void setSrcItemId(Long srcItemId) {
        this.srcItemId = srcItemId;
    }

    public Long getSrcDocumentId() {
        return srcDocumentId;
    }

    public void setSrcDocumentId(Long srcDocumentId) {
        this.srcDocumentId = srcDocumentId;
    }

    public Long getDstDocumentId() {
        return dstDocumentId;
    }

    public void setDstDocumentId(Long dstDocumentId) {
        this.dstDocumentId = dstDocumentId;
    }

    public Long getLinkTypeId() {
        return linkTypeId;
    }

    public void setLinkTypeId(Long linkTypeId) {
        this.linkTypeId = linkTypeId;
    }

    public String getLinkComment() {
        return linkComment;
    }

    public void setLinkComment(String linkComment) {
        this.linkComment = linkComment;
    }

    public Long getDstItemId() {
        return dstItemId;
    }

    public void setDstItemId(Long dstItemId) {
        this.dstItemId = dstItemId;
    }
}
