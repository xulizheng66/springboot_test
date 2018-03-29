package com.turbur.controller;

import com.turbur.entity.User;
import com.turbur.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/userList")
    public List<User> findAll(){

        List<User> users = userService.getAll();

        return users;
    }

    @RequestMapping(value = "/insert")
    public void insert(){
        User user = new User();
        user.setUserId(4);
        user.setUserName("xulizheng");
        userService.insert(user);
    }

}
