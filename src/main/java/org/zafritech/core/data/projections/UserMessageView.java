/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.projections;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import org.zafritech.core.data.domain.UserMessage;

/**
 *
 * @author LukeS
 */
@Projection(name = "UserMessageView", types = UserMessage.class)
public interface UserMessageView {
    
    String getId();
    
    @Value("#{target.uuId}")
    String getUuId();
    
    @Value("#{target.message.subject}")
    String getSubject();
    
    @Value("#{target.message.message}")
    String getMessage();
    
    @Value("#{target.message.sender.firstName} #{target.message.sender.lastName}")
    String getSender();
    
    @Value("#{target.message.sender.email}")
    String getSenderEmail();
    
    @Value("#{target.user.firstName} #{target.user.lastName}")
    String getReciever();
    
    @Value("#{target.user.email}")
    String getRecieverEmail();
    
    @Value("#{target.messageBox}")
    String getMessageBox();
    
    String getStatus();
    
    Date getStatusDate();
}
