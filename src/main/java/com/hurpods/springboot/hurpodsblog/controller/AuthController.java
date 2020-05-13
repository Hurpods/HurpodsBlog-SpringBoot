package com.hurpods.springboot.hurpodsblog.controller;

import com.hurpods.springboot.hurpodsblog.pojo.User;
import com.hurpods.springboot.hurpodsblog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/todos")
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @PreAuthorize("hasRole('User')")
    @RequestMapping("/test1")
    public String test1(){
        return "测试普通用户";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/test2")
    public String test2(){
        return "测试admin";
    }

    @RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
    public User register(String username,String password, HttpServletRequest request) throws AuthenticationException{
        return authService.register(username,password,request);
    }

    @RequestMapping("/login")
    public String login(String username, String password){
        return authService.login(username,password);
    }
}
