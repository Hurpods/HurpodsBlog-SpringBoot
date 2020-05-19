package com.hurpods.springboot.hurpodsblog.controller;

import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ContentController {
    @Autowired
    BookService bookService;

    @GetMapping("/books")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result getAllBooks() {
        return ResultFactory.buildSuccessResult(bookService.getAllBooks());
    }
}
