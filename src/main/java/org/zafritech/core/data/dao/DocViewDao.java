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
public class DocViewDao {
    
    private Long Id;
    
    private String identifier;
    
    private String documentName;
    
    private String projectName;
    
    private String infoClass;
    
    private String version;
    
    private String modDate;

    public DocViewDao() {
        
    }

    public DocViewDao(String identifier, 
                      String documentName, 
                      String projectName, 
                      String infoClass, 
                      String version, 
                      String modDate) {
        
        this.identifier = identifier;
        this.documentName = documentName;
        this.projectName = projectName;
        this.infoClass = infoClass;
        this.version = version;
        this.modDate = modDate;
    }

    @Override
    public String toString() {
        
        return "DocViewDao{" + "Id=" + Id + ", identifier=" + 
                identifier + ", documentName=" + documentName + ", projectName=" + 
                projectName + ", infoClass=" + infoClass + ", version=" + 
                version + ", modDate=" + modDate + '}';
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

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getInfoClass() {
        return infoClass;
    }

    public void setInfoClass(String infoClass) {
        this.infoClass = infoClass;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModDate() {
        return modDate;
    }

    public void setModDate(String modDate) {
        this.modDate = modDate;
    }
}
