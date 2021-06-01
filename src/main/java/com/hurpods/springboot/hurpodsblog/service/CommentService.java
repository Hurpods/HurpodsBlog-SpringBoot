package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.dto.CommentDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Comment;
import com.hurpods.springboot.hurpodsblog.result.Result;

import java.util.List;

public interface CommentService {
    Result postComment(CommentDTO dto);

    Result getCommentByContentId(Integer status,Integer id);

    Result deleteCommentById(Integer id);

    List<Comment> getAllComments();

    Integer getCommentNumber();

    Result fuzzySearch(String keywords);

    Result batchDelete(List<Integer> idList);
}
