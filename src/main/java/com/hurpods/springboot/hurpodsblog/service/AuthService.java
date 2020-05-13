package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.pojo.User;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    User register(String username,String password, HttpServletRequest request);
    String login(String username,String password);
}
