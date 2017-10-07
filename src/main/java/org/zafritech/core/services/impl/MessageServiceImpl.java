/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.management.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.converters.DaoToMessageConverter;
import org.zafritech.core.data.dao.MailBoxDao;
import org.zafritech.core.data.dao.MsgDao;
import org.zafritech.core.data.dao.PageNavigationDao;
import org.zafritech.core.data.domain.Message;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserMessage;
import org.zafritech.core.data.repositories.MessageRepository;
import org.zafritech.core.data.repositories.UserMessageRepository;
import org.zafritech.core.enums.MessageBox;
import org.zafritech.core.enums.MessageStatusType;
import org.zafritech.core.services.MessageService;
import org.zafritech.core.services.UserService;
import org.zafritech.core.data.projections.UserMessageView;
import org.zafritech.core.services.CommonService;

/**
 *
 * @author LukeS
 */
@Service
public class MessageServiceImpl implements MessageService {
    
    @Autowired
    private CommonService commonService;
     
    @Autowired
    private UserService userService;
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    public MessageService messageService;
    
    @Autowired
    private UserMessageRepository userMessageRepository;
    
    @Override
    public Message sendMessage(MsgDao msgDao) {
        
        DaoToMessageConverter daoToMsgConverter = new DaoToMessageConverter();
        Set<User> recipients = new HashSet<User>();
        User sender = userService.loggedInUser();
        List<User> users = userService.allUser();
        users.forEach(user->recipients.add(user));  
        
        Message message = daoToMsgConverter.convert(msgDao);
        message.setSender(sender); 
        Message msg = messageRepository.save(message);
        
        // Update sender's OUTBOX
        UserMessage senderMessage = new UserMessage(msg, sender, MessageBox.OUT);
        userMessageRepository.save(senderMessage); 
        
        // Send to everybody's INBOX
        for (User receipient : recipients) {
            
            UserMessage userMessage = new UserMessage(msg, receipient, MessageBox.IN);
            userMessageRepository.save(userMessage); 
        }
        
        return msg; 
    }

    @Override
    public List<UserMessage> getAllIncomingMessages(User user) {
        
        List<UserMessage> inbox = userMessageRepository.findByUserAndMessageBoxOrderByStatusDateDesc(user, MessageBox.IN);
        
        return inbox;
    }

    @Override
    public List<UserMessage> getIncomingMessages(User user, Integer pageSize, Integer pageNumber) {
        
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "statusDate");
        
        List<UserMessage> messages = new ArrayList<>();
        userMessageRepository.findByUserAndMessageBoxOrderByStatusDateDesc(request, user, MessageBox.IN).forEach(messages::add);
        
        return messages;
    }
    
    @Override
    public MailBoxDao getMessageBox(Integer pageSize, Integer pageNumber, MessageBox box) {
        
        String mailBoxNname = "inbox";
        
        User user = userService.loggedInUser();
        List<UserMessageView> mailbox = userMessageRepository.findUserMessageViewByUserAndMessageBoxOrderByStatusDateDesc(user, box);
        PageNavigationDao navigator = commonService.getPageNavigator(mailbox.size(), pageSize, pageNumber);
        
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "statusDate");
        List<UserMessageView> messages = new ArrayList<>();
        userMessageRepository.findUserMessageViewByUserAndMessageBoxOrderByStatusDateDesc(request, user, box).forEach(messages::add);
        
        switch (box) {
            case IN:
                mailBoxNname = "inbox";
                break;
            case OUT:
                mailBoxNname = "outbox";
                break;
            case DRAFT:
                mailBoxNname = "draft";
                break;
            default:
                break;
        }
        
        MailBoxDao messageBox = new MailBoxDao(user, 
                                               messages, 
                                               mailBoxNname, 
                                               pageNumber, 
                                               pageSize, 
                                               navigator.getItemCount(), 
                                               navigator.getItemCount(), 
                                               navigator.getPageList(), 
                                               navigator.getPageCount(), 
                                               navigator.getLastPage());
        
        return messageBox;
    }

    @Override
    public List<UserMessageView> getIncomingMessageViews(User user, Integer pageSize, Integer pageNumber) {
        
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "statusDate");
        
        List<UserMessageView> messages = new ArrayList<>();
        userMessageRepository.findUserMessageViewByUserAndMessageBoxOrderByStatusDateDesc(request, user, MessageBox.IN).forEach(messages::add);
        
        return messages;
    }

    @Override
    public List<UserMessageView> getSentUserMessageView(User user, Integer pageSize, Integer pageNumber) {
        
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "statusDate");
        
        List<UserMessageView> messages = new ArrayList<>();
        userMessageRepository.findUserMessageViewByUserAndMessageBoxOrderByStatusDateDesc(request, user, MessageBox.OUT).forEach(messages::add);
        
        return messages;
    }

    @Override
    public List<UserMessage> getUnreadMessages(User user) {

        List<UserMessage> unread = userMessageRepository.findByUserAndMessageBoxAndStatus(user, MessageBox.IN, MessageStatusType.RECEIVED);
        
        return unread;
    }

    @Override
    public List<UserMessageView> getUnreadMessageViews(User user) {

        List<UserMessageView> unread = userMessageRepository.findUserMessageViewByUserAndMessageBoxAndStatus(user, MessageBox.IN, MessageStatusType.SENT);
        
        return unread;
    }

    @Override
    public List<UserMessage> getAllSentMessages(User user) {

        List<UserMessage> sent = userMessageRepository.findByUserAndMessageBoxOrderByStatusDateDesc(user, MessageBox.OUT);
        
        return sent;
    }

    @Override
    public List<UserMessage> getSentMessages(User user, Integer pageSize, Integer pageNumber) {

        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "statusDate");
        
        List<UserMessage> messages = new ArrayList<>();
        userMessageRepository.findByUserAndMessageBoxOrderByStatusDateDesc(request, user, MessageBox.OUT).forEach(messages::add);
        
        return messages;
    }

    @Override
    public List<UserMessage> getAllDraftMessages(User user) {

        List<UserMessage> draft = userMessageRepository.findByUserAndMessageBoxAndStatus(user, MessageBox.OUT, MessageStatusType.DRAFT);
        
        return draft;
    }

    @Override
    public List<UserMessage> getDraftMessages(User user, Integer pageSize, Integer pageNumber) {

        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "statusDate");
        
        List<UserMessage> messages = new ArrayList<>();
        userMessageRepository.findByUserAndMessageBoxOrderByStatusDateDesc(request, user, MessageBox.DRAFT).forEach(messages::add);
        
        return messages;
    }

    @Override
    public List<UserMessage> getAllDeletedMessages(User user) {
        
        List<UserMessage> trash = userMessageRepository.findByUserAndMessageBoxAndStatus(user, MessageBox.IN, MessageStatusType.DELETED);
        
        return trash;
    }

    @Override
    public List<UserMessage> getDeletedMessages(User user, Integer pageSize, Integer pageNumber) {
        
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "statusDate");
        
        List<UserMessage> messages = new ArrayList<>();
        userMessageRepository.findByUserAndMessageBoxOrderByStatusDateDesc(request, user, MessageBox.TRASH).forEach(messages::add);
        
        return messages;
    }

    @Override
    public List<Notification> getUnreadNotifications(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setMessageRead(UserMessage message, User user) {
        
        message.setStatus(MessageStatusType.READ); 
        userMessageRepository.save(message);
    }
}
