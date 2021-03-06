package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedDate;

@Entity(name = "CORE_MESSAGES")
public class Message implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    @OneToOne
    @JoinColumn(name = "senderId")
    private User sender;
    
    @Column(columnDefinition = "TEXT")
    private String subject;
    
    @Column(columnDefinition = "TEXT")
    private String message;
        
    @ManyToOne
    @JoinColumn(name = "parentId")
    private Message parentMessage;
    
    @ManyToOne
    @JoinColumn(name = "attachmentId")
    private MessageAttachment attachment;
        
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date messageDate;
    
    public Message() {
        
    }

    public Message(String subject, String message) {
        
        this.uuId = UUID.randomUUID().toString();
        this.subject = subject;
        this.message = message;
        this.messageDate = new Timestamp(System.currentTimeMillis());
    }

    public Message(String subject, String message, User sender) {
        
        this.uuId = UUID.randomUUID().toString();
        this.subject = subject;
        this.message = message;
        this.sender = sender;
        this.messageDate = new Timestamp(System.currentTimeMillis());
    }

    public Message(String subject, String message, Message parentMessage) {
        
        this.uuId = UUID.randomUUID().toString();
        this.subject = subject;
        this.message = message;
        this.parentMessage = parentMessage;
        this.messageDate = new Timestamp(System.currentTimeMillis());
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

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(Message parentMessage) {
        this.parentMessage = parentMessage;
    }

    public MessageAttachment getAttachment() {
        return attachment;
    }

    public void setAttachment(MessageAttachment attachment) {
        this.attachment = attachment;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

}

