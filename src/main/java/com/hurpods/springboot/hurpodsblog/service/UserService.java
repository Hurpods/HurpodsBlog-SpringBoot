package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.dto.LoginRequest;
import com.hurpods.springboot.hurpodsblog.dto.RegisterRequest;
import com.hurpods.springboot.hurpodsblog.pojo.User;
import com.hurpods.springboot.hurpodsblog.result.Result;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    List<User> getAllUser();

    User getUserById(@Param("userId") Integer userId);

    User getUserByOthers(@Param("value") String value);

    void deleteUserById(@Param("userId") Integer userId);

    User registerUser(RegisterRequest registerRequest, HttpServletRequest request);

    Result loginUser(LoginRequest loginRequest, HttpServletRequest request);

    void updateUserInfo(User user);

    void updateUserPassword(User user);

    Result validateUsername(String username);

    Result validateTelephone(String telephone);

    Result validateEmail(String email);
}
