package com.hurpods.springboot.hurpodsblog.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {
    private Integer userId;

    private String userName;

    private String userPassword;

    private String userNickName;

    private String userEmail;

    private String userTel;

    private String userAvatar;

    private String lastLoginIp;

    private Date registerTime;

    private Date lastLoginTime;

    private String province;

    private String city;

    private Boolean isAdmin;

    private List<Role> roles;
}