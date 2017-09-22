package org.zafritech.core.data.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedDate;
import org.zafritech.core.enums.MessageBox;
import org.zafritech.core.enums.MessageStatusType;

@Entity(name = "CORE_USER_MESSAGES")
public class UserMessage implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String uuId;
    
    @ManyToOne
    @JoinColumn(name = "messageId")
    private Message message;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    
    @Enumerated(EnumType.STRING)
    private MessageBox messageBox;
    
    @Enumerated(EnumType.STRING)
    private MessageStatusType status;
    
    private boolean available;
    
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusDate;

    public UserMessage() {
        
    }

    public UserMessage(Message message, User user, MessageBox box) {
        
        this.uuId = UUID.randomUUID().toString();
        this.message = message;
        this.user = user;
        this.messageBox = box;
        this.status = (box.equals(MessageBox.OUT)) ? MessageStatusType.SENT : MessageStatusType.RECEIVED;
        this.statusDate = new Timestamp(System.currentTimeMillis());
        this.available = true;
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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MessageBox getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(MessageBox messageBox) {
        this.messageBox = messageBox;
    }

    public MessageStatusType getStatus() {
        return status;
    }

    public void setStatus(MessageStatusType status) {
        this.status = status;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }
}

