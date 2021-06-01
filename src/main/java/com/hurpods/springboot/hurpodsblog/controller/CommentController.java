package com.hurpods.springboot.hurpodsblog.controller;

import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hurpods.springboot.hurpodsblog.dto.CommentDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Comment;
import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultCode;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("")
    public Result postComment(@RequestBody CommentDTO dto) {
        return commentService.postComment(dto);
    }

    @GetMapping("/{status}/{id}")
    public Result GetCommentByContent(@PathVariable String status, @PathVariable Integer id) {
        switch (status) {
            case "reporter":
                return commentService.getCommentByContentId(1, id);
            case "article":
                return commentService.getCommentByContentId(0, id);
            default:
                break;
        }
        return ResultFactory.buildFailureResult(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_JUDGEMENT')")
    public Result deleteCommentById(@PathVariable Integer id) {
        return commentService.deleteCommentById(id);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_JUDGEMENT')")
    public Result getAllComments(Integer pageNum) {
        if (pageNum != null) {
            PageHelper.startPage(pageNum, 12);
        }
        Integer size = commentService.getCommentNumber();
        List<Comment> commentList = commentService.getAllComments();
        for (Comment comment : commentList) {
            comment.setCommentContent(HtmlUtil.filter(HtmlUtil.unescape(comment.getCommentContent())));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", size);
        map.put("commentList", commentList);
        return commentList.size() != 0 ?
                ResultFactory.buildSuccessResult(map) :
                ResultFactory.buildCustomFailureResult(ResultCode.RESULT_DATA_NONE, "无数据");
    }

    @GetMapping("/search/{keywords}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_JUDGEMENT')")
    public Result fuzzySearch(@PathVariable String keywords) {
        return commentService.fuzzySearch(keywords);
    }


    @PutMapping("/batchDelete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_JUDGEMENT')")
    public Result batchDelete(@RequestBody String ids) {
        JSONObject json = new JSONObject(ids);
        List<Integer> idList = (List<Integer>) json.get("ids");
        return commentService.batchDelete(idList);
    }
}
