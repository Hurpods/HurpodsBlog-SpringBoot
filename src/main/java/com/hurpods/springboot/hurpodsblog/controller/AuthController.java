package com.hurpods.springboot.hurpodsblog.controller;

import com.hurpods.springboot.hurpodsblog.dto.RegisterUser;
import com.hurpods.springboot.hurpodsblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String registerUser(RegisterUser registerUser){
        if(!registerUser.getPassword().equals(registerUser.getRePassword())){
            return "false";
        }
        return "";
    }
}
