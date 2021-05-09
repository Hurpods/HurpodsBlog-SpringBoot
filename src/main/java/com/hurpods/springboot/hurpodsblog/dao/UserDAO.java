package com.hurpods.springboot.hurpodsblog.dao;

import com.hurpods.springboot.hurpodsblog.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO {
    List<User> getAllUser();

    User getUserById(@Param("userId") Integer userId);

    User getUserByUsername(@Param("username") String username);

    User getUserByOthers(@Param("value") String value);

    int deleteUserById(@Param("userId") Integer userId);

    Integer deleteUserByUsername(@Param("userName") String username);

    void registerUser(User user);

    Integer updateUserInfo(User user);

    Integer updateUserPassword(User user);

    Integer getNumber();

    List<User> getSpecial();

    List<User> fuzzySearch(@Param("keywords") String keywords);

    Integer banUser(@Param("idList") List<Integer> idList);
}
