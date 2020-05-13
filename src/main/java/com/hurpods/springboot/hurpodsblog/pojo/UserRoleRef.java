package com.hurpods.springboot.hurpodsblog.pojo;

import lombok.Data;

@Data
public class UserRoleRef {
    private Integer id;

    private Integer userId;

    private Integer roleId;
}