/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.services;

import org.zafritech.core.data.domain.Document;

/**
 *
 * @author LukeS
 */
public interface ItemPDFService {
    
     byte[] getItemsPDFDocument(Document document) throws Exception;
}
