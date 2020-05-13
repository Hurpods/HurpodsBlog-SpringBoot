package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.pojo.Role;

public interface RoleService {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}
