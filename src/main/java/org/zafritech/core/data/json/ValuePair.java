/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.json;

/**
 *
 * @author LukeS
 */
public class ValuePair {
   
    private String x;
    
    private Integer y;

    public ValuePair(String x, Integer y) {
        
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        
        return "ValuePair{" + "x=" + x + ", y=" + y + '}';
    }
    
    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
