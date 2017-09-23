/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import org.zafritech.core.data.dao.ReferenceDao;
import org.zafritech.core.data.domain.Reference;

/**
 *
 * @author LukeS
 */
public interface ReferenceService {
    
    Reference addDocumentReference(ReferenceDao refDao);
}
