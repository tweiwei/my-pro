package com.my.ut.init;

import com.my.ut.entity.User;
import com.my.ut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDataInit implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("-------------UserDataInit-----------");
        User user = userService.getUserById(1);

        System.out.println("-----user="+user);
    }
}
