package com.hurpods.springboot.hurpodsblog.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Role implements GrantedAuthority {
    private Integer roleId;

    private String roleName;

    private String roleNameZh;

    public Role() {

    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}