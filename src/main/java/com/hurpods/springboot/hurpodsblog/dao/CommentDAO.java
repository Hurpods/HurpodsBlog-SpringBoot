package com.hurpods.springboot.hurpodsblog.dao;

import com.hurpods.springboot.hurpodsblog.pojo.Article;
import com.hurpods.springboot.hurpodsblog.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDAO {
    int insertComment(Comment comment);

    List<Comment> getCommentByContentId(Integer status,Integer id);

    int deleteCommentById(Integer id);
}
