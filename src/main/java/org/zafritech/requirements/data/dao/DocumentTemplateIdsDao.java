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
public class DocumentTemplateIdsDao {
    
    private Long documentId;
    
    private Long templateId;

    public DocumentTemplateIdsDao() {
        
    }

    @Override
    public String toString() {
        
        return "DocumentTemplateIdsDao{" + "documentId=" 
                + documentId + ", templateId=" + templateId + '}';
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
}
