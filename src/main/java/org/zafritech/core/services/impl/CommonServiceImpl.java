/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.dao.PageNavigationDao;
import org.zafritech.core.services.CommonService;

/**
 *
 * @author LukeS
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public PageNavigationDao getPageNavigator(Integer itemsCount, Integer pageSize, Integer pageNumber) {
        
        PageNavigationDao navigator = new PageNavigationDao();
        
        Integer pageCount = (int)Math.ceil((float)(itemsCount / pageSize)) + 1;
        List<Integer> pageList = getPagesList(pageNumber, pageCount);
        
        navigator.setItemCount(itemsCount); 
        navigator.setPageCount(pageCount);
        navigator.setPageList(pageList); 
        navigator.setLastPage(Collections.max(pageList));
        
        return navigator;
    }
    
    private  List<Integer> getPagesList(int currentPage, int lastPage) {
        
        List<Integer> pageList = new ArrayList<>();

        int startIndex = 1;
        int upperLimit = 1;
        
        if (lastPage < 9) {
            
            startIndex = 1;
            upperLimit = lastPage;
            
        } else {

            upperLimit = ((int) Math.ceil((double)currentPage / 9) * 9);
            upperLimit = (lastPage < upperLimit) ? lastPage : upperLimit;
            startIndex = upperLimit - 8;
        
        }
        
        for (int i = startIndex; i <= upperLimit; i++) {

            pageList.add(i);
        }
        
        return pageList;
    }
}
