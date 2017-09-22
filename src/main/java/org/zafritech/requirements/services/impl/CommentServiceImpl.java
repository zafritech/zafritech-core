/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.services.UserService;
import org.zafritech.requirements.data.dao.CommentDao;
import org.zafritech.requirements.data.domain.Item;
import org.zafritech.requirements.data.domain.ItemComment;
import org.zafritech.requirements.data.repositories.ItemCommentRepository;
import org.zafritech.requirements.data.repositories.ItemRepository;
import org.zafritech.requirements.services.CommentService;

/**
 *
 * @author LukeS
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemCommentRepository commentRepository;
    
    @Override
    public ItemComment saveRquirementsComment(CommentDao commentDao) {
        
        Item item = itemRepository.findOne(commentDao.getItemId());
        User author = userService.loggedInUser();
        
        ItemComment comment = new ItemComment(itemRepository.findOne(commentDao.getItemId()), commentDao.getComment(), author);
        comment = commentRepository.save(comment);
        
        item.getComments().add(comment);
        itemRepository.save(item);
        
        return commentRepository.save(comment);
    }
    
}
