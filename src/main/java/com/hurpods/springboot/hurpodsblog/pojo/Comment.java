package com.hurpods.springboot.hurpodsblog.pojo;

import com.hurpods.springboot.hurpodsblog.dto.CommentDTO;
import com.hurpods.springboot.hurpodsblog.vo.UserVo;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer commentId;
    private String commentContent;
    private Integer commentAuthorId;
    private Timestamp commentPostTime;
    private Integer status;
    private Integer contentId;
    private UserVo user;

    public Comment(){}
    public Comment(CommentDTO dto){
        this.commentContent= dto.getCommentContent();
        this.commentAuthorId=dto.getCommentAuthorId();
        this.status=dto.getStatus();
        this.contentId=dto.getContentId();
    }
}
