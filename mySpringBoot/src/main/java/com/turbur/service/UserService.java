package com.turbur.service;

import com.turbur.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    void insert(User user);
}
