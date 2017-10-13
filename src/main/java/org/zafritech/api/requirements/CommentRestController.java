/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.api.requirements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zafritech.requirements.data.dao.CommentDao;
import org.zafritech.requirements.data.dao.LinkDao;
import org.zafritech.requirements.data.domain.ItemComment;
import org.zafritech.requirements.services.CommentService;

/**
 *
 * @author LukeS
 */
@RestController
public class CommentRestController {
    
    @Autowired
    private CommentService commentService;
    
    @RequestMapping(value = "/api/requirements/document/items/comment/save", method = RequestMethod.POST)
    public ResponseEntity<ItemComment> newRquirementsSaveItem(@RequestBody CommentDao commentDao) {
        
        ItemComment comment = commentService.saveRquirementsComment(commentDao);

        return new ResponseEntity<ItemComment>(comment, HttpStatus.OK);
    }
}
