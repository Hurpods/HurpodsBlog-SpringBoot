package com.hurpods.springboot.hurpodsblog.pojo;

import lombok.Data;

@Data
public class Role {
    private Integer roleId;

    private String roleName;

    private String roleDescription;

    public Role() {

    }
}