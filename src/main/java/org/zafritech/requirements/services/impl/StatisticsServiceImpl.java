/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.domain.Project;
import org.zafritech.core.data.json.Series;
import org.zafritech.core.data.json.ValuePair;
import org.zafritech.requirements.data.domain.Item;
import org.zafritech.requirements.data.domain.ItemCategory;
import org.zafritech.requirements.data.repositories.ItemCategoryRepository;
import org.zafritech.requirements.data.repositories.ItemRepository;
import org.zafritech.requirements.enums.ItemStatus;
import org.zafritech.requirements.services.StatisticsService;

/**
 *
 * @author LukeS
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Value("${zafritech.paths.data-dir}")
    private String data_dir;
  
    @Autowired
    private ItemRepository itemRepository;
     
    @Autowired
    private ItemCategoryRepository itemCategoryRepository;
       
    @Override
    public List<Object> fetchSnapshotData(Project project, String fromDate) {
        
        /************************
        * TBD
        ************************/
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> fetchSnapshotData(Project project, String fromDate, String toDate) {
        
        /************************
        * TBD
        ************************/
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> fetchStatusData(Project project) {
        
        String dataFile = data_dir + "json/category-status-all.json";
        ObjectMapper mapper = new ObjectMapper();
        List<Object> data = new ArrayList<>();
        
        for (ItemStatus itemStatus : ItemStatus.values()) {

            List<ItemCategory> categories = new ArrayList<>();
            itemCategoryRepository.findByProjectOrderByCategoryNameAsc(project).forEach(categories::add);  

            Series series = new Series();
            List<ValuePair> valuePairList = new ArrayList<>();

            Integer total = 0;

            for (ItemCategory category : categories) {

                List<Item> items = itemRepository.findByItemCategoryAndItemStatus(category, itemStatus);

                if (items.size() > 0) {

                    ValuePair valuePair = new ValuePair(category.getCategoryCode(), items.size());
                    valuePairList.add(valuePair);
                }

                total = total + items.size();
            }

            if (total > 0) {

                series.setKey(itemStatus.name()); 
                series.setValues(valuePairList); 
                data.add(series);
            }
        }
        
        try {
            
            mapper.writeValue(new File(dataFile), data);
            
        } catch (IOException ex) {
            
            Logger.getLogger(ItemServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }

    @Override
    public List<Object> fetchStatusData(Project project, ItemCategory category) {
        
        String dataFile = data_dir + "json/category-status-" + category.getUuId() + ".json";
        ObjectMapper mapper = new ObjectMapper();
        List<Object> data = new ArrayList<>();
        
        for (ItemStatus itemStatus : ItemStatus.values()) {
            
            List<ItemCategory> categories = new ArrayList<>();
            itemCategoryRepository.findByProjectOrderByCategoryNameAsc(project).forEach(categories::add);  

            Series series = new Series();
            List<ValuePair> valuePairList = new ArrayList<>();
            
            List<Item> items = itemRepository.findByItemCategoryAndItemStatus(category, itemStatus);

            if (items.size() > 0) {

                ValuePair valuePair = new ValuePair(category.getCategoryCode(), items.size());
                valuePairList.add(valuePair);

                series.setKey(itemStatus.name()); 
                series.setValues(valuePairList); 
                data.add(series);
            }
        }
        
        try {
            
            mapper.writeValue(new File(dataFile), data);
            
        } catch (IOException ex) {
            
            Logger.getLogger(ItemServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }
}
