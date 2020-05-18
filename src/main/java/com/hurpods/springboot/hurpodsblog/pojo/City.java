package com.hurpods.springboot.hurpodsblog.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class City implements Serializable {
    private static final long serialVersionUID = -4290241184641956886L;
    String id;
    String codeP;
    String name;
    String code;
}
