package com.hurpods.springboot.hurpodsblog.dao;

import com.hurpods.springboot.hurpodsblog.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO {
    List<User> getAllUser();

    User getUserById(@Param("userId") Integer userId);

    User getUserByUsername(@Param("username")String username);

    User getUserByOthers(@Param("value") String value);

    void deleteUserById(@Param("userId") Integer userId);

    void registerUser(User user);

    void updateUserInfo(User user);

    void updateUserPassword(User user);
}
