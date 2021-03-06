package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.dto.RegisterRequest;
import com.hurpods.springboot.hurpodsblog.dto.UpdateRequest;
import com.hurpods.springboot.hurpodsblog.pojo.User;
import com.hurpods.springboot.hurpodsblog.result.Result;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    List<User> getAllUser();

    User getUserById(@Param("userId") Integer userId);

    User getUserByUsername(@Param("username") String username);

    User getUserByOthers(@Param("value") String value);

    Result deleteUserById(@Param("userId") Integer userId);

    Result deleteUserByUsername(String password, @Param("userName") String username);

    User registerUser(RegisterRequest registerRequest, HttpServletRequest request);

    Result updateUser(UpdateRequest updateRequest, Integer userId);

    Result updateUserInfo(UpdateRequest updateRequest, String username);

    Result updateUserPassword(UpdateRequest updateRequest, String username);

    Result validateUsername(String username);

    Result validateTelephone(String telephone);

    Result validateEmail(String email);

    Integer getNumber();

    List<User> getSpecial();

    List<User> fuzzySearch(@Param("keywords") String keywords);

    Integer banUser(List<Integer> idList);

    Result unbanUser(int id);

    int uploadAvatar(int id, String s);
}
