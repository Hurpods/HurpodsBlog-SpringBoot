package com.hurpods.springboot.hurpodsblog.dao;

import com.hurpods.springboot.hurpodsblog.pojo.UserRoleRef;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRefDAO {
    void createUserRoleRef(UserRoleRef userRoleRef);
}
