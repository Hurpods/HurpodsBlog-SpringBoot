package com.hurpods.springboot.hurpodsblog.controller;

import com.hurpods.springboot.hurpodsblog.dto.LoginRequest;
import com.hurpods.springboot.hurpodsblog.dto.RegisterRequest;
import com.hurpods.springboot.hurpodsblog.pojo.User;
import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultCode;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Result registerUser(RegisterRequest registerRequest, HttpServletRequest request) {
        User user = userService.registerUser(registerRequest, request);
        if (user != null) {
            return ResultFactory.buildSuccessResult(user);
        } else {
            return ResultFactory.buildFailureResult(ResultCode.PARAM_IS_INVALID);
        }
    }

    @PostMapping("/validate")
    public Result validateInfo(String value, String type) {
        Result result = new Result();
        switch (type) {
            case "username":
                result = userService.validateUsername(value);
                break;
            case "telephone":
                result = userService.validateTelephone(value);
                break;
            case "email":
                result = userService.validateEmail(value);
                break;
        }
        LOGGER.info("validate" + type);
        return result;
    }

    @PostMapping("/login")
    public Result login(LoginRequest loginRequest, HttpServletRequest request) {
        userService.loginUser(loginRequest, request);
        return ResultFactory.buildSuccessResult(loginRequest);
    }
}