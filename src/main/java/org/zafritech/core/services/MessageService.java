/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services;

import java.util.List;
import javax.management.Notification;
import org.zafritech.core.data.dao.MailBoxDao;
import org.zafritech.core.data.dao.MsgDao;
import org.zafritech.core.data.domain.Message;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserMessage;
import org.zafritech.core.data.projections.UserMessageView;
import org.zafritech.core.enums.MessageBox;

/**
 *
 * @author LukeS
 */
public interface MessageService {
    
    Message sendMessage(MsgDao msgDao);
    
    List<UserMessage> getAllIncomingMessages(User user);
    
    List<UserMessage> getIncomingMessages(User user, Integer pageSize, Integer pageNumber);
    
    List<UserMessage> getUnreadMessages(User user);
    
    List<UserMessage> getAllSentMessages(User user);
    
    List<UserMessage> getSentMessages(User user, Integer pageSize, Integer pageNumber);
    
    List<UserMessage> getAllDraftMessages(User user);
    
    List<UserMessage> getDraftMessages(User user, Integer pageSize, Integer pageNumber);
    
    List<Notification> getUnreadNotifications(User user);
    
    List<UserMessage> getAllDeletedMessages(User user);
    
    List<UserMessage> getDeletedMessages(User user, Integer pageSize, Integer pageNumber);
    
    void setMessageRead(UserMessage message, User user);
    
    // Project based servieces
    MailBoxDao getMessageBox(Integer pageSize, Integer pageNumber, MessageBox box);
    
    List<UserMessageView> getUnreadMessageViews(User user);
    
    List<UserMessageView> getIncomingMessageViews(User user, Integer pageSize, Integer pageNumber);
    
    List<UserMessageView> getSentUserMessageView(User user, Integer pageSize, Integer pageNumber);
}
