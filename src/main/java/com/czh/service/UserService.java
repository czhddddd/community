package com.czh.service;

import com.czh.mapper.UserMapper;
import com.czh.modle.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：czh
 * @description：TODO
 * @date ：2020/4/14 2:15 下午
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User creatOrUpdate(User user){
        User byAccountId = userMapper.findByAccountId(user.getAccountId());
        //根据accountid检验重复用户
        if (byAccountId.getAccountId().equals(user.getAccountId())){
            userMapper.updateUser(user);
        }else {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
        return user;
    }


}
