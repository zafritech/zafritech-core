/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.json;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LukeS
 */
public class Series {
    
    private String key;
    
    private List<ValuePair> values = new ArrayList<>();

    public Series() {
        
    }

    @Override
    public String toString() {
        
        return "Series{" + "label=" + getKey() + ", values=" + getValues() + '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<ValuePair> getValues() {
        return values;
    }

    public void setValues(List<ValuePair> values) {
        this.values = values;
    }
}
