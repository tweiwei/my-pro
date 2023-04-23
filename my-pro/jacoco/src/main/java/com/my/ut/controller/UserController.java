package com.my.ut.controller;

import com.my.ut.entity.User;
import com.my.ut.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/selectUser",method = RequestMethod.GET)
    public String selectUserById(@RequestParam int id){
        User user = userService.getUserById(id);
        return user.toString();
    }

}
