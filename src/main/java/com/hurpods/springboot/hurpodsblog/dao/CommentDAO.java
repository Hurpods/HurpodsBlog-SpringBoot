package com.hurpods.springboot.hurpodsblog.dao;

import com.hurpods.springboot.hurpodsblog.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentDAO {
    int insertComment(Comment comment);
}
