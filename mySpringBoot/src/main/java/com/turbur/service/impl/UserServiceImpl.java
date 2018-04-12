package com.turbur.service.impl;

import com.turbur.entity.User;
import com.turbur.mapper.UserMapper;
import com.turbur.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.Contended;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    @Transactional
    public void insert(User user) {

        userMapper.insert(user);
    }
}
