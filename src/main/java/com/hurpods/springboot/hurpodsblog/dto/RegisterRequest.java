package com.hurpods.springboot.hurpodsblog.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String rePassword;
    private String telephone;
    private String email;
}
