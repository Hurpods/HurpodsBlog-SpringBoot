package com.hurpods.springboot.hurpodsblog.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRoleRef implements Serializable {
    private static final long serialVersionUID = 1205825852562031979L;
    private Integer id;

    private Integer userId;

    private Integer roleId;
}