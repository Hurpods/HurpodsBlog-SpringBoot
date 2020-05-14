package com.hurpods.springboot.hurpodsblog.dto;

import lombok.Data;

@Data
public class LoginUser {
    private String username;
    private String password;
    private boolean rememberMe;
}
