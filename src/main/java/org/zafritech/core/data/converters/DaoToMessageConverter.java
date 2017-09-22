/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.converters;

import org.springframework.core.convert.converter.Converter;
import org.zafritech.core.data.dao.MsgDao;
import org.zafritech.core.data.domain.Message;

/**
 *
 * @author LukeS
 */
public class DaoToMessageConverter implements Converter<MsgDao, Message> {

    @Override
    public Message convert(MsgDao msgDao) {
        
        Message msg = new Message(msgDao.getSubject(), msgDao.getMessage());
        
        return msg;
    }
}
