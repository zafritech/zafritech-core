/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.zafritech.core.enums.TemplateVariableCategories;

/**
 *
 * @author LukeS
 */
@Entity(name = "CORE_TEMPLATE_VARIABLES")
public class TemplateVariable implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private TemplateVariableCategories category;
    
    private String variable;
    
    private String value;

    public TemplateVariable() {
        
    }

    public TemplateVariable(TemplateVariableCategories category, String variable, String value) {
        
        this.category = category;
        this.variable = variable;
        this.value = value;
    }

    @Override
    public String toString() {
        
        return "TemplateVariable {" + "id=" + getId() + ", property=" + getVariable() + ", value=" + getValue() + '}';
    }

    public Long getId() {
        return id;
    }

    public TemplateVariableCategories getCategory() {
        return category;
    }

    public void setCategory(TemplateVariableCategories category) {
        this.category = category;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
