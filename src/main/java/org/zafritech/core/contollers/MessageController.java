/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zafritech.core.data.dao.MailBoxDao;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserMessage;
import org.zafritech.core.data.repositories.UserMessageRepository;
import org.zafritech.core.enums.MessageBox;
import org.zafritech.core.services.MessageService;
import org.zafritech.core.services.UserService;

/**
 *
 * @author LukeS
 */
@Controller
public class MessageController {
    
    @Autowired
    private UserService userService;
 
    @Autowired
    public MessageService messageService;
  
    @Autowired
    private UserMessageRepository userMessageRepository;
           
    @RequestMapping(value = {"/messages", "/messages/inbox"})
    public String getMessagesBoxes(@RequestParam(name = "s", defaultValue = "15") Integer pageSize,
                                   @RequestParam(name = "p", defaultValue = "1") Integer pageNumber,
                                   Model model) {
        
        MailBoxDao inbox = messageService.getMessageBox(pageSize, pageNumber, MessageBox.IN);
        MailBoxDao sentbox = messageService.getMessageBox(pageSize, pageNumber, MessageBox.OUT);
        MailBoxDao draftbox = messageService.getMessageBox(pageSize, pageNumber, MessageBox.DRAFT);
        
        model.addAttribute("inbox", inbox);
        model.addAttribute("sentbox", sentbox);
        model.addAttribute("draftbox", draftbox);
          
        return "views/messages/mailbox";
    }
   
    @RequestMapping("/messages/{uuid}")
    public String readMessage(@PathVariable(value = "uuid") String uuid, Model model) {
        
        User user = userService.loggedInUser();
        UserMessage message = userMessageRepository.findByUuId(uuid);
        messageService.setMessageRead(message, user);
                
        model.addAttribute("user", user);
        model.addAttribute("message", message);
        model.addAttribute("msgbox", "");
        model.addAttribute("newLineChar", "\n");
        
        return "views/messages/message";
    }
}
