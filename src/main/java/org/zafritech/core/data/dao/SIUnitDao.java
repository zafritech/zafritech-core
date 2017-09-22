/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.dao;

/**
 *
 * @author LukeS
 */
public class SIUnitDao {
    
    private String symbol;
    
    private String name;
    
    private String definition;

    @Override
    public String toString() {
        
        return "SIUnitDao{" + "symbol=" + getSymbol() + ", name=" 
                + getName() + ", definition=" + getDefinition() + '}';
    }

    public SIUnitDao() {
        
    }

    public SIUnitDao(String symbol, String name, String definition) {
        
        this.symbol = symbol;
        this.name = name;
        this.definition = definition;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
