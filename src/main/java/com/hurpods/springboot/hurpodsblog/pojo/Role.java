package com.hurpods.springboot.hurpodsblog.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {
    private static final long serialVersionUID = -2733738983946654308L;
    private Integer roleId;

    private String roleName;

    private String roleDescription;
}