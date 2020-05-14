package com.hurpods.springboot.hurpodsblog.dto;

import lombok.Data;

@Data
public class RegisterUser {
    private String username;
    private String password;
    private String rePassword;
    private Integer telephone;
    private String email;
}
