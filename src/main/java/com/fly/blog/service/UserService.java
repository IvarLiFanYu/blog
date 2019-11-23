package com.fly.blog.service;

import com.fly.blog.po.User;

public interface UserService {
    /**查询用户是否存在.*/
    User checkUser(String username, String password);
}
