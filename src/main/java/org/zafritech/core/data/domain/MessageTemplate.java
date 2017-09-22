package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedDate;

@Entity(name = "CORE_MESSAGE_TEMPLATES")
public class MessageTemplate implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    private String templateName;
    
    private String messageSubject;
    
    @Column(columnDefinition = "TEXT")
    private String templateValue;
    
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public MessageTemplate() {
        
    }

    public MessageTemplate(String templateName, String messageSubject, String templateValue) {
        
        this.uuId = UUID.randomUUID().toString();
        this.templateName = templateName;
        this.messageSubject = messageSubject;
        this.templateValue = templateValue;
        this.createdDate = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    public String getTemplateValue() {
        return templateValue;
    }

    public void setTemplateValue(String templateValue) {
        this.templateValue = templateValue;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}

