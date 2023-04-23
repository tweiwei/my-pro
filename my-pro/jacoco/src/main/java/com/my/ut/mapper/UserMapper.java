package com.my.ut.mapper;

import com.my.ut.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User selectUserById(int id){

        User user = new User();
        user.setId(id);
        user.setName(RandomStringUtils.random(4));

        return user;
    }

}
