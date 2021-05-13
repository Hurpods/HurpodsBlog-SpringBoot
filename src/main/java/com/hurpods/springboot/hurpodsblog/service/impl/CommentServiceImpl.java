package com.hurpods.springboot.hurpodsblog.service.impl;

import com.hurpods.springboot.hurpodsblog.dao.CommentDAO;
import com.hurpods.springboot.hurpodsblog.dto.CommentDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Comment;
import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDAO commentDAO;

    @Override
    public Result postComment(CommentDTO dto) {
        Comment comment = new Comment(dto);
        Timestamp nowTime = new Timestamp(new Date().getTime());
        comment.setCommentPostTime(nowTime);

        int num = commentDAO.insertComment(comment);

        return num == 1 ? ResultFactory.buildSuccessResult("发表成功") : ResultFactory.buildFailureResult("unknown error");
    }
}
