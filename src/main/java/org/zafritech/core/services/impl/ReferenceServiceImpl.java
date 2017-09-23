/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.ReferenceDao;
import org.zafritech.core.data.domain.Reference;
import org.zafritech.core.services.ReferenceService;

/**
 *
 * @author LukeS
 */
@Service
public class ReferenceServiceImpl implements ReferenceService {

    @Override
    public Reference addDocumentReference(ReferenceDao refDao) {
        
        if (refDao.getProjectRefId() != null) {
            
            
        }
            
        return null;
    }
}
