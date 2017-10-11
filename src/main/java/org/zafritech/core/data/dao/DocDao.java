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
public class DocDao {
    
    private Long Id;
    
    private String identifier;
    
    private Long projectId;
    
    private Long wbsId;
    
    private Long folderId;
    
    private Long typeId;
    
    private Long decriptorId;
    
    private Long infoClassId;
    
    private String documentName;
    
    private String documentLongName;
    
    private String documentDescription;

    public DocDao() {
        
    }

    @Override
    public String toString() {
        
        return "DocDao{" + "Id=" + Id + ", identifier=" + identifier 
                + ", projectId=" + projectId + ", wbsId=" + wbsId 
                + ", folderId=" + folderId + ", typeId=" + typeId 
                + ", decriptorId=" + decriptorId + ", infoClassId=" 
                + infoClassId + ", documentName=" + documentName 
                + ", documentLongName=" + documentLongName + ", documentDescription=" 
                + documentDescription + '}';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public Long getDecriptorId() {
        return decriptorId;
    }

    public void setDecriptorId(Long decriptorId) {
        this.decriptorId = decriptorId;
    }

    public Long getInfoClassId() {
        return infoClassId;
    }

    public void setInfoClassId(Long infoClassId) {
        this.infoClassId = infoClassId;
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
}
