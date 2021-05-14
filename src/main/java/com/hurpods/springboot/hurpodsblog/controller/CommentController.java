package com.hurpods.springboot.hurpodsblog.controller;

import com.hurpods.springboot.hurpodsblog.dto.CommentDTO;
import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultCode;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/comment")
    public Result postComment(@RequestBody CommentDTO dto) {
        return commentService.postComment(dto);
    }

    @GetMapping("/comment/{status}/{id}")
    public Result GetCommentByContent(@PathVariable String status, @PathVariable Integer id) {
        switch (status) {
            case "reporter":
                return commentService.getCommentByContentId(1,id);
            case "article":
                return commentService.getCommentByContentId(0,id);
            default:
                break;
        }
        return ResultFactory.buildFailureResult(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
    }

    @DeleteMapping("/comment/{id}")
    public Result deleteCommentById(@PathVariable Integer id){
        return commentService.deleteCommentById(id);
    }
}
