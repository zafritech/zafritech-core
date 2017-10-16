/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.data.dao.reqif;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.sql.Timestamp;
import java.util.UUID;

/**
 *
 * @author LukeS
 */
@JacksonXmlRootElement(localName = "THE-HEADER")
public class ReqIfTheHeader {
    
    // REQ-IF-HEADER
    @JacksonXmlProperty(localName="REQ-IF-HEADER")
    private ReqIfHeader reqIfHeader;

    public ReqIfTheHeader() {
        
    }
    
    public ReqIfTheHeader(ReqIfHeader reqIfHeader) {
        
        this.reqIfHeader = reqIfHeader;
    }
  
    public ReqIfHeader getReqIfHeader() {
        return reqIfHeader;
    }

    public void setReqIfHeader(ReqIfHeader reqIfHeader) {
        this.reqIfHeader = reqIfHeader;
    }
    
    // Properties
    public class ReqIfHeader {
        
        @JacksonXmlProperty(isAttribute=true, localName="IDENTIFIER")
        private String identifier;

        @JacksonXmlProperty(localName="COMMENT")
        private String comment;

        @JacksonXmlProperty(localName="CREATION-TIME")
        private String creationTime;

        @JacksonXmlProperty(localName="REPOSITORY-ID")
        private String repositoryId;

        @JacksonXmlProperty(localName="REQ-IF-TOOL-ID")
        private String ReqIfToolId;

        @JacksonXmlProperty(localName="REQ-IF-VERSION")
        private String ReqIfVersion;

        @JacksonXmlProperty(localName="SOURCE-TOOL-ID")
        private String sourceToolId;

        @JacksonXmlProperty(localName="TITLE")
        private String title;

        public ReqIfHeader() {
            
        }

        public ReqIfHeader(String comment, 
                           String repositoryId, 
                           String ReqIfToolId, 
                           String ReqIfVersion, 
                           String sourceToolId, 
                           String title) {
            
            this.identifier = UUID.randomUUID().toString().toUpperCase();
            this.comment = comment;
            this.creationTime = new Timestamp(System.currentTimeMillis()).toString();
            this.repositoryId = repositoryId;
            this.ReqIfToolId = ReqIfToolId;
            this.ReqIfVersion = ReqIfVersion;
            this.sourceToolId = sourceToolId;
            this.title = title;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }

        public String getRepositoryId() {
            return repositoryId;
        }

        public void setRepositoryId(String repositoryId) {
            this.repositoryId = repositoryId;
        }

        public String getReqIfToolId() {
            return ReqIfToolId;
        }

        public void setReqIfToolId(String ReqIfToolId) {
            this.ReqIfToolId = ReqIfToolId;
        }

        public String getReqIfVersion() {
            return ReqIfVersion;
        }

        public void setReqIfVersion(String ReqIfVersion) {
            this.ReqIfVersion = ReqIfVersion;
        }

        public String getSourceToolId() {
            return sourceToolId;
        }

        public void setSourceToolId(String sourceToolId) {
            this.sourceToolId = sourceToolId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
