package com.hurpods.springboot.hurpodsblog.controller;

import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.BookService;
import com.hurpods.springboot.hurpodsblog.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    BookService bookService;

    @Autowired
    ContentService contentService;

    @GetMapping("/reporter/{id}")
    public Result getReporterByBook(@PathVariable Integer id) {
        return ResultFactory.buildSuccessResult(contentService.getReporterById(id));
    }

    @GetMapping("/reporter")
    public Result getReporter() {
        return ResultFactory.buildSuccessResult(contentService.getAllReporters());
    }

    @GetMapping("/article/{id}")
    public Result getArticleById(@PathVariable Integer id) {
        return ResultFactory.buildSuccessResult(contentService.getArticleById(id));
    }

    @GetMapping("/article")
    public Result getArticle() {
        return ResultFactory.buildSuccessResult(contentService.getAllArticle());
    }

    @GetMapping("/carousel")
    public Result getCarousel() {
        return ResultFactory.buildSuccessResult(contentService.getCarousel());
    }
}
