/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import java.util.AbstractMap;
import java.util.List;

/**
 *
 * @author LukeS
 */
public interface DocumentPDFService {
    
    void addMetadata(Document document, Long id);
            
    void addFrontPage(Document document, Long id) throws DocumentException;
    
    void addApprovalPage(Document document, Long id) throws DocumentException;
    
    void addDistributionSheet(Document document, Long id) throws DocumentException;
    
    void addChangeControlSheet(Document document, Long id) throws DocumentException;
    
    void addTableOfContents(Document document,  List<AbstractMap.SimpleEntry<String, Integer>> entries) throws DocumentException;
    
    void addEmptyLine(Paragraph paragraph, Integer lines);
}
