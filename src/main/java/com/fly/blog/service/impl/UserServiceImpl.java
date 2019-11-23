package com.fly.blog.service.impl;

import com.fly.blog.dao.UserRepository;
import com.fly.blog.po.User;
import com.fly.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUserNameAndPassword(username, password);
        return user;
    }
}
