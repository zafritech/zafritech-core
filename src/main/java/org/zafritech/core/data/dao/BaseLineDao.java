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
public class BaseLineDao {
    
    private Long documentId;
    
    private Long entityTypeId;
    
    private String baseLineName;
    
    private String baseLineDescription;

    public BaseLineDao() {
        
    }

    public BaseLineDao(Long documentId, Long entityTypeId, String baseLineName, String baseLineDescription) {
        
        this.documentId = documentId;
        this.entityTypeId = entityTypeId;
        this.baseLineName = baseLineName;
        this.baseLineDescription = baseLineDescription;
    }

    @Override
    public String toString() {
        
        return "BaseLineDao{" + "documentId=" + getDocumentId() 
                + ", entityTypeId=" + getEntityTypeId() + ", baseLineName=" 
                + getBaseLineName() + ", baseLineDescription=" 
                + getBaseLineDescription() + '}';
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(Long entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public String getBaseLineName() {
        return baseLineName;
    }

    public void setBaseLineName(String baseLineName) {
        this.baseLineName = baseLineName;
    }

    public String getBaseLineDescription() {
        return baseLineDescription;
    }

    public void setBaseLineDescription(String baseLineDescription) {
        this.baseLineDescription = baseLineDescription;
    }
}
