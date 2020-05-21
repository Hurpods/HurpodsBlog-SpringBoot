package com.hurpods.springboot.hurpodsblog.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Book implements Serializable {
    private static final long serialVersionUID = 7455904738614557941L;
    private Integer bookId;
    private String bookCover;
    private String bookTitle;
    private String bookAuthor;
    private String postDate;
    private String bookPress;
    private String bookAbs;
    private Integer cid;
    private Category category;
}
