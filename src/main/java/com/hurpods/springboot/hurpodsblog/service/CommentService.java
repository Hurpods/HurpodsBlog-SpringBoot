package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.dto.CommentDTO;
import com.hurpods.springboot.hurpodsblog.result.Result;

public interface CommentService {
    Result postComment(CommentDTO dto);

    Result getCommentByContentId(Integer status,Integer id);
}
