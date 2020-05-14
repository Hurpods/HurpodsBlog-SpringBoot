package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    List<User> getAllUser();

    User getUserById(@Param("userId") Integer userId);

    User getUserByOthers(@Param("value") String value);

    void deleteUserById(@Param("userId") Integer userId);

    void registerUser(User user);

    void updateUserInfo(User user);

    void updateUserPassword(User user);
}
