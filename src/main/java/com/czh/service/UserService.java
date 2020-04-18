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
        try{
            User byAccountId = userMapper.findByAccountId(user.getAccountId());
            if (byAccountId == null){
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                userMapper.insert(user);
            }else {
                userMapper.updateUser(user);
            }

        }catch (Exception e){
        }
        return user;
    }


}
