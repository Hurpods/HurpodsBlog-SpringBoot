package com.hurpods.springboot.hurpodsblog.pojo;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 2303937118397714454L;
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

    private String userLocate;

    private Boolean enabled;

    private List<Role> roles;

    public List<SimpleGrantedAuthority> getSecurityRoles() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));
        return authorities;
    }
}