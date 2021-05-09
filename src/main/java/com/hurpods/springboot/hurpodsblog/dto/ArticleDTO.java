package com.hurpods.springboot.hurpodsblog.dto;

import lombok.Data;

@Data
public class ArticleDTO {
    private String title;
    private String content;
    private String userName;
    private String cover;
}
