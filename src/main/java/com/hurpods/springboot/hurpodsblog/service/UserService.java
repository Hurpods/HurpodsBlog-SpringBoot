package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.pojo.User;

public interface UserService {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    User selectByOthers(String value);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}
