/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.dao;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author LukeS
 */
public class ExcelItemsDao {
    
    private Long documentId;
    private MultipartFile excelFile;

    public ExcelItemsDao() {
        
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public MultipartFile getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(MultipartFile excelFile) {
        this.excelFile = excelFile;
    }
}
