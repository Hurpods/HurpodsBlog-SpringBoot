package com.hurpods.springboot.hurpodsblog.dto;

import lombok.Data;

@Data
public class UpdateRequest {
    private String userName;
    private String nickName;
    private String telephone;
    private String email;
    private String[] locate;
    private String roles;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
