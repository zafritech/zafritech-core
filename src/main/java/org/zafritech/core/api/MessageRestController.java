/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.core.data.dao.MsgDao;
import org.zafritech.core.data.dao.MsgTmplDao;
import org.zafritech.core.data.domain.Message;
import org.zafritech.core.data.domain.MessageTemplate;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserMessage;
import org.zafritech.core.data.repositories.MessageTemplateRepository;
import org.zafritech.core.data.repositories.UserMessageRepository;
import org.zafritech.core.data.repositories.UserRepository;
import org.zafritech.core.enums.MessageBox;
import org.zafritech.core.enums.MessageStatusType;
import org.zafritech.core.services.MessageService;
import org.zafritech.core.services.UserService;
import org.zafritech.core.data.projections.UserMessageView;


/**
 *
 * @author LukeS
 */
@RestController
public class MessageRestController {
    
    @Autowired
    public MessageTemplateRepository msgTmplRepository;
    
    @Autowired
    public MessageService messageService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMessageRepository userMessageRepository;
    
    @RequestMapping(value = "/api/messages/message/new", method = POST)
    public ResponseEntity<Message> newMessage(@RequestBody MsgDao msgDao) {
        
        Message msg = messageService.sendMessage(msgDao);

        return new ResponseEntity<Message>(msg, HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/messages/list", method = RequestMethod.GET)
    public ResponseEntity<List<UserMessage>> getMessagesList() {
        
        List<UserMessage> messages = userMessageRepository.findByUserAndMessageBox(userService.loggedInUser(), MessageBox.IN); 
        
        return new ResponseEntity<List<UserMessage>>(messages, HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/messages/unread/list", method = RequestMethod.GET)
    public ResponseEntity<List<UserMessageView>> getUnreadMessagesList() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userRepository.findByUserName(name);
        
        List<UserMessageView> messages = userMessageRepository.findUserMessageViewByUserAndMessageBoxAndStatus(user, MessageBox.IN, MessageStatusType.RECEIVED); 
        
        return new ResponseEntity<List<UserMessageView>>(messages, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/messages/templates/new", method = POST)
    public ResponseEntity<MessageTemplate> newMessageTemplate(@RequestBody MsgTmplDao tmplDao) {
        
        MessageTemplate tmpl = msgTmplRepository.findByTemplateName(tmplDao.getTemplateName());
        
        if (tmpl == null) {
        
            MessageTemplate template = new MessageTemplate(tmplDao.getTemplateName(),
                                                           tmplDao.getMessageSubject(),
                                                           tmplDao.getTemplateValue().trim());
            
            tmpl = msgTmplRepository.save(template);
            
        } else {
            
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<MessageTemplate>(tmpl, HttpStatus.OK);
    }
        
    @RequestMapping(value="/api/messages/templates/list", method = RequestMethod.GET)
    public ResponseEntity<List<MessageTemplate>> getMessageTemplatesList() {

        List<MessageTemplate> templates = msgTmplRepository.findAllByOrderByTemplateName(); 
        
        return new ResponseEntity<List<MessageTemplate>>(templates, HttpStatus.OK);
    }
        
    @RequestMapping(value="/api/messages/templates/find/{id}", method = RequestMethod.GET)
    public ResponseEntity<MessageTemplate> getMessageTemplate(@PathVariable(value = "id") Long id) {

        MessageTemplate template = msgTmplRepository.findOne(id);  
        
        return new ResponseEntity<MessageTemplate>(template, HttpStatus.OK);
    }
}
