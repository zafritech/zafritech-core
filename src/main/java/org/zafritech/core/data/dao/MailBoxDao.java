/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.dao;

import java.util.List;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.projections.UserMessageView;

/**
 *
 * @author LukeS
 */
public class MailBoxDao {
    
    private User user;
    
    private List<UserMessageView> messages;
    
    private String mailBoxName;
    
    private Integer page;
    
    private Integer pageSize;
    
    private Integer mailBoxSize;
    
    private Integer messageCount;
    
    private List<Integer> pageList;
    
    private Integer pageCount;
    
    private Integer lastPage;

    public MailBoxDao() {
        
    }

    public MailBoxDao(User user, 
                      List<UserMessageView> messages, 
                      String mailBoxName, 
                      Integer page, 
                      Integer pageSize, 
                      Integer mailBoxSize, 
                      Integer messageCount, 
                      List<Integer> pageList, 
                      Integer pageCount, 
                      Integer lastPage) {
        
        this.user = user;
        this.messages = messages;
        this.mailBoxName = mailBoxName;
        this.page = page;
        this.pageSize = pageSize;
        this.mailBoxSize = mailBoxSize;
        this.messageCount = messageCount;
        this.pageList = pageList;
        this.pageCount = pageCount;
        this.lastPage = lastPage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserMessageView> getMessages() {
        return messages;
    }

    public void setMessages(List<UserMessageView> messages) {
        this.messages = messages;
    }

    public String getMailBoxName() {
        return mailBoxName;
    }

    public void setMailBoxName(String mailBoxName) {
        this.mailBoxName = mailBoxName;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getMailBoxSize() {
        return mailBoxSize;
    }

    public void setMailBoxSize(Integer mailBoxSize) {
        this.mailBoxSize = mailBoxSize;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public List<Integer> getPageList() {
        return pageList;
    }

    public void setPageList(List<Integer> pageList) {
        this.pageList = pageList;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }
}
