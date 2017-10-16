/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.dao.reqif;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

/**
 *
 * @author LukeS
 */
@JacksonXmlRootElement(localName = "CORE-CONTENT")
public class ReqIfContentDao {
    
    @JacksonXmlProperty(localName="REQ-IF-CONTENT")
    private ReqIfContent reqIfContent;
    
    public class ReqIfContent {
        
        @JacksonXmlProperty(localName="DATATYPES")
        private List<ReqIfDataTypeDao> dataTypes;
        
        
    }
}
