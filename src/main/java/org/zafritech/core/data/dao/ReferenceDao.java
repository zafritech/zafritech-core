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
public class ReferenceDao {
    
    private String source;
    private Long documentId;
    private Long projectId;
    private Long itemId;
    private Long projectRefId;
    private Long libraryRefId;
    private String linkRefId;
    private String linkRefUniqueId;
    private String linkRefTitle;
    private String linkRefUrl;
    private String linkRefAuthority;

    public ReferenceDao() {
        
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getProjectRefId() {
        return projectRefId;
    }

    public void setProjectRefId(Long projectRefId) {
        this.projectRefId = projectRefId;
    }

    public Long getLibraryRefId() {
        return libraryRefId;
    }

    public void setLibraryRefId(Long libraryRefId) {
        this.libraryRefId = libraryRefId;
    }

    public String getLinkRefId() {
        return linkRefId;
    }

    public void setLinkRefId(String linkRefId) {
        this.linkRefId = linkRefId;
    }

    public String getLinkRefUniqueId() {
        return linkRefUniqueId;
    }

    public void setLinkRefUniqueId(String linkRefUniqueId) {
        this.linkRefUniqueId = linkRefUniqueId;
    }

    public String getLinkRefTitle() {
        return linkRefTitle;
    }

    public void setLinkRefTitle(String linkRefTitle) {
        this.linkRefTitle = linkRefTitle;
    }

    public String getLinkRefUrl() {
        return linkRefUrl;
    }

    public void setLinkRefUrl(String linkRefUrl) {
        this.linkRefUrl = linkRefUrl;
    }

    public String getLinkRefAuthority() {
        return linkRefAuthority;
    }

    public void setLinkRefAuthority(String linkRefAuthority) {
        this.linkRefAuthority = linkRefAuthority;
    }
}
