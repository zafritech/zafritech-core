/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.dao;

import java.util.List;

/**
 *
 * @author LukeS
 */
public class PlotSeriesDao {
    
    private String key;
    
    private List<Object[]> values;

    public PlotSeriesDao() {
        
    }

    public PlotSeriesDao(String key, List<Object[]> values) {
        
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Object[]> getValues() {
        return values;
    }

    public void setValues(List<Object[]> values) {
        this.values = values;
    }
}
