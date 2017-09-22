/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.contollers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zafritech.core.data.dao.PageNavigationDao;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserMessage;
import org.zafritech.core.data.repositories.UserMessageRepository;
import org.zafritech.core.enums.MessageBox;
import org.zafritech.core.services.CommonService;
import org.zafritech.core.services.MessageService;
import org.zafritech.core.services.UserService;
import org.zafritech.core.data.projections.UserMessageView;

/**
 *
 * @author LukeS
 */
@Controller
public class MessageController {
    
    @Autowired
    private CommonService commonService;
     
    @Autowired
    private UserService userService;
 
    @Autowired
    public MessageService messageService;
  
    @Autowired
    private UserMessageRepository userMessageRepository;
           
    @RequestMapping(value = {"/messages", "/messages/inbox"})
    public String messagesInbox(@RequestParam(name = "s", defaultValue = "15") Integer pageSize,
                                @RequestParam(name = "p", defaultValue = "1") Integer pageNumber,
                                Model model) {
        
        User user = userService.loggedInUser();
        List<UserMessageView> inbox = userMessageRepository.findUserMessageViewByUserAndMessageBoxOrderByStatusDateDesc(user, MessageBox.IN);
        PageNavigationDao navigator = commonService.getPageNavigator(inbox.size(), pageSize, pageNumber);
        
        List<UserMessageView> messages = messageService.getIncomingMessageViews(user, pageSize, pageNumber);
        
        model.addAttribute("user", user);
        model.addAttribute("messages", messages);
        model.addAttribute("msgBox", "inbox");
        model.addAttribute("page", pageNumber);
        model.addAttribute("size", pageSize);
        model.addAttribute("msgBoxSize", navigator.getItemCount());
        model.addAttribute("msgCount", navigator.getItemCount());
        model.addAttribute("list", navigator.getPageList());
        model.addAttribute("count", navigator.getPageCount());
        model.addAttribute("last", navigator.getLastPage());
          
        return "views/messages/mailbox";
    }
    
    @RequestMapping(value = {"/messages/sent"})
    public String sentMessages(@RequestParam(name = "s", defaultValue = "15") Integer pageSize,
                               @RequestParam(name = "p", defaultValue = "1") Integer pageNumber,
                               Model model) {
        
        User user = userService.loggedInUser();
        List<UserMessageView> outbox = userMessageRepository.findUserMessageViewByUserAndMessageBoxOrderByStatusDateDesc(user, MessageBox.OUT);
        PageNavigationDao navigator = commonService.getPageNavigator(outbox.size(), pageSize, pageNumber);
        
        List<UserMessageView> messages = messageService.getSentUserMessageView(user, pageSize, pageNumber); 
        
        model.addAttribute("user", user);
        model.addAttribute("messages", messages);
        model.addAttribute("msgBox", "outbox");
        model.addAttribute("page", pageNumber);
        model.addAttribute("size", pageSize);
        model.addAttribute("msgBoxSize", navigator.getItemCount());
        model.addAttribute("msgCount", navigator.getItemCount());
        model.addAttribute("list", navigator.getPageList());
        model.addAttribute("count", navigator.getPageCount());
        model.addAttribute("last", navigator.getLastPage());
          
        return "views/messages/mailbox";
    }
    
    @RequestMapping(value = {"/messages/draft"})
    public String draftMessages(@RequestParam(name = "s", defaultValue = "15") Integer pageSize,
                                @RequestParam(name = "p", defaultValue = "1") Integer pageNumber,
                                Model model) {
        
        User user = userService.loggedInUser();
        List<UserMessage> inbox = userMessageRepository.findByUserAndMessageBoxOrderByStatusDateDesc(user, MessageBox.DRAFT);
        PageNavigationDao navigator = commonService.getPageNavigator(inbox.size(), pageSize, pageNumber);
        
        List<UserMessage> messages = messageService.getDraftMessages(user, pageSize, pageNumber);
        
        model.addAttribute("user", user);
        model.addAttribute("messages", messages);
        model.addAttribute("msgBox", "draft");
        model.addAttribute("page", pageNumber);
        model.addAttribute("size", pageSize);
        model.addAttribute("msgBoxSize", navigator.getItemCount());
        model.addAttribute("msgCount", navigator.getItemCount());
        model.addAttribute("list", navigator.getPageList());
        model.addAttribute("count", navigator.getPageCount());
        model.addAttribute("last", navigator.getLastPage());
          
        return "views/messages/mailbox";
    }
    
    @RequestMapping("/messages/trash")
    public String trashedItems(@RequestParam(name = "s", defaultValue = "15") Integer pageSize,
                               @RequestParam(name = "p", defaultValue = "1") Integer pageNumber,
                               Model model) {
        
        User user = userService.loggedInUser();
        List<UserMessage> inbox = userMessageRepository.findByUserAndMessageBoxOrderByStatusDateDesc(user, MessageBox.TRASH);
        PageNavigationDao navigator = commonService.getPageNavigator(inbox.size(), pageSize, pageNumber);
        
        List<UserMessage> messages = messageService.getDraftMessages(user, pageSize, pageNumber);
        
        model.addAttribute("user", user);
        model.addAttribute("messages", messages);
        model.addAttribute("msgBox", "trash");
        model.addAttribute("page", pageNumber);
        model.addAttribute("size", pageSize);
        model.addAttribute("msgBoxSize", navigator.getItemCount());
        model.addAttribute("msgCount", navigator.getItemCount());
        model.addAttribute("list", navigator.getPageList());
        model.addAttribute("count", navigator.getPageCount());
        model.addAttribute("last", navigator.getLastPage());
        
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
