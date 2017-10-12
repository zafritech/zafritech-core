/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.services;

import org.zafritech.core.data.domain.Document;
import org.zafritech.requirements.data.domain.Template;

/**
 *
 * @author LukeS
 */
public interface TemplateItemService {
    
    Template createTemplateFromDocument(Document document);
    
    Document importTemplateToDocument(Template template);
}
