/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zafritech.core.data.converters;

import org.springframework.core.convert.converter.Converter;
import org.zafritech.core.data.domain.User;
import org.zafritech.core.data.dao.UserDao;

/**
 *
 * @author LukeS
 */
public class UserToDaoConverter implements Converter<User, UserDao> {

    @Override
    public UserDao convert(User user) {

        UserDao userDao = new UserDao();

        userDao.setId(user.getId());
        userDao.setEmail(user.getEmail());
        userDao.setPassword(user.getPassword());

        return userDao;
    }
}
