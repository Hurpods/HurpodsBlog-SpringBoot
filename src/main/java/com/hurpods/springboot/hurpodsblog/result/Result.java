package com.hurpods.springboot.hurpodsblog.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 346749689722228300L;

    private Integer code;
    private String message;
    private Object data;

    public Result(){}

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
