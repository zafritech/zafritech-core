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
public class DocEditDao {
    
    private Long Id;
    
    private Long projectId;
    
    private Long wbsId;
    
    private Long folderId;
    
    private Long typeId;
    
    private Long infoClassId;
    
    private Long ownerId;
    
    private String identifier;
    
    private String documentName;
    
    private String documentLongName;
    
    private String documentDescription;
    
    private String documentIssue;
    
    private String status;

    public DocEditDao() {
        
    }

    @Override
    public String toString() {
        
        return "DocEditDao{" + "Id=" + getId() + ", projectId=" + 
                getProjectId() + ", folderId=" + getFolderId() + ", typeId=" + 
                getTypeId() + ", infoClassId=" + getInfoClassId() + ", ownerId=" + 
                getOwnerId() + ", identifier=" + getIdentifier() + ", documentName=" + 
                getDocumentName() + ", documentDescription=" + 
                getDocumentDescription() + ", documentIssue=" + 
                getDocumentIssue() + ", status=" + getStatus() + '}';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getWbsId() {
        return wbsId;
    }

    public void setWbsId(Long wbsId) {
        this.wbsId = wbsId;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getInfoClassId() {
        return infoClassId;
    }

    public void setInfoClassId(Long infoClassId) {
        this.infoClassId = infoClassId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentLongName() {
        return documentLongName;
    }

    public void setDocumentLongName(String documentLongName) {
        this.documentLongName = documentLongName;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public String getDocumentIssue() {
        return documentIssue;
    }

    public void setDocumentIssue(String documentIssue) {
        this.documentIssue = documentIssue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
