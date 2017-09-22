/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.requirements.services;

import org.zafritech.requirements.data.dao.CommentDao;
import org.zafritech.requirements.data.domain.ItemComment;

/**
 *
 * @author LukeS
 */
public interface CommentService {
    
    ItemComment saveRquirementsComment(CommentDao commentDao);
}
