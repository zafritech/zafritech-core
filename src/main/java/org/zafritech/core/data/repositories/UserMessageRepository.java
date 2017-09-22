/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.domain.UserMessage;
import org.zafritech.core.enums.MessageBox;
import org.zafritech.core.enums.MessageStatusType;
import org.zafritech.core.data.projections.UserMessageView;

/**
 *
 * @author LukeS
 */
public interface UserMessageRepository extends CrudRepository<UserMessage, Long> {
    
    UserMessage findByUuId(String uuid);
    
    List<UserMessage> findByUserAndMessageBox(User user, MessageBox box);
    
    List<UserMessage> findByUserAndMessageBoxOrderByStatusDateDesc(User user, MessageBox box);
    
    Page<UserMessage> findByUserAndMessageBoxOrderByStatusDateDesc(Pageable pageable, User user, MessageBox box);
    
    List<UserMessage> findByUserAndMessageBoxAndStatus(User user, MessageBox box, MessageStatusType status);
    
    List<UserMessage> findByUserAndMessageBoxOrderByStatusDateDesc(User user, MessageBox box, MessageStatusType status);
    
    // Projection Views
    List<UserMessageView> findUserMessageViewByUserAndMessageBoxOrderByStatusDateDesc(User user, MessageBox box);
    
    Page<UserMessageView> findUserMessageViewByUserAndMessageBoxOrderByStatusDateDesc(Pageable pageable, User user, MessageBox box);
    
    List<UserMessageView> findUserMessageViewByUserAndMessageBoxAndStatus(User user, MessageBox box, MessageStatusType status);
}
