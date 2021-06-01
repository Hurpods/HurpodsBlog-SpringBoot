package com.hurpods.springboot.hurpodsblog.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private String commentContent;
    private Integer commentAuthorId;
    private Integer status;
    private Integer contentId;
}
