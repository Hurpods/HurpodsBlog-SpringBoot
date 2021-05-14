package com.hurpods.springboot.hurpodsblog.service.impl;

import cn.hutool.http.HtmlUtil;
import com.hurpods.springboot.hurpodsblog.dao.CommentDAO;
import com.hurpods.springboot.hurpodsblog.dto.CommentDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Article;
import com.hurpods.springboot.hurpodsblog.pojo.Comment;
import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultCode;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDAO commentDAO;

    @Override
    public Result postComment(CommentDTO dto) {
        Comment comment = new Comment(dto);
        Timestamp nowTime = new Timestamp(new Date().getTime());
        String content = HtmlUtil.escape(comment.getCommentContent());

        comment.setCommentContent(content);
        comment.setCommentPostTime(nowTime);

        int num = commentDAO.insertComment(comment);

        return num == 1 ? ResultFactory.buildSuccessResult("发表成功") : ResultFactory.buildFailureResult("unknown error");
    }

    @Override
    public Result getCommentByContentId(Integer status,Integer id) {
        List<Comment> commentList = commentDAO.getCommentByContentId(status,id);
        for (Comment comment : commentList) {
            comment.setCommentContent(HtmlUtil.unescape(comment.getCommentContent()));
        }
        return commentList.size() != 0 ?
                ResultFactory.buildSuccessResult(commentList) :
                ResultFactory.buildFailureResult(ResultCode.RESULT_DATA_NONE);
    }
}
