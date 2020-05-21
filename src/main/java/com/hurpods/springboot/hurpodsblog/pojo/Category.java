package com.hurpods.springboot.hurpodsblog.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Category implements Serializable {
    private static final long serialVersionUID = -1909921654024783109L;
    private Integer categoryId;
    private String categoryName;
    List<Book> bookList;
}
