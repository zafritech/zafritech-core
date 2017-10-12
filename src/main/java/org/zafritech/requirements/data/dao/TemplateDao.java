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
public class TemplateDao {
    
    private Long documentId;
    
    private Long documentTypeId;
    
    private String templateName;
    
    private String templateLongName;
    
    private String templateDescription;

    public TemplateDao() {
        
    }

    @Override
    public String toString() {
        
        return "TemplateDao{" + "documentId=" + getDocumentId() 
                + ", documentTypeId=" + getDocumentTypeId() + ", templateName=" 
                + getTemplateName() + ", templateLongName=" + getTemplateLongName() 
                + ", templateDescription=" + getTemplateDescription() + '}';
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateLongName() {
        return templateLongName;
    }

    public void setTemplateLongName(String templateLongName) {
        this.templateLongName = templateLongName;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }
}
