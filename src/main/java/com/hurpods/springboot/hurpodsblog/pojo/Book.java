package com.hurpods.springboot.hurpodsblog.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Book implements Serializable {
    private static final long serialVersionUID = 7455904738614557941L;
    private Integer id;
    private String cover;
    private String title;
    private String author;
    private String date;
    private String press;
    private String abs;
    private Integer categoryId;
}
