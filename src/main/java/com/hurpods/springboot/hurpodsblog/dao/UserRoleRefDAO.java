package com.hurpods.springboot.hurpodsblog.dao;

import com.hurpods.springboot.hurpodsblog.pojo.UserRoleRef;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRefDAO {
    void createUserRoleRef(UserRoleRef userRoleRef);

    UserRoleRef getURRByUserId(@Param("userId") Integer userId);

    Integer updateURR(UserRoleRef userRoleRef);
}
