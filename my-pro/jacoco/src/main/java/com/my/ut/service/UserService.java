package com.my.ut.service;

import com.my.ut.entity.User;
import com.my.ut.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;


    public User getUserById(int id){
        if(id == 0) {
            throw new NullPointerException("id is zero.");
        }

        return userMapper.selectUserById(id);
    }


}
