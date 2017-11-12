/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.PageNavigationDao;

/**
 *
 * @author LukeS
 */
@Service
public interface CommonService {
    
    PageNavigationDao getPageNavigator(Integer itemsCount, Integer pageSize, Integer pageNumber);
}
