package com.hurpods.springboot.hurpodsblog.controller;

import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultCode;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content")
public class ContentController {
    @Autowired
    BookService bookService;

    @GetMapping("/books")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result getAllBooks() {
        return ResultFactory.buildSuccessResult(bookService.getAllBooks());
    }

    @GetMapping("/cats")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result getAllCats() {
        return ResultFactory.buildSuccessResult(bookService.getAllCats());
    }

    @GetMapping("/cat/{catId}/books")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result getBooksByCat(@PathVariable Integer catId) {
        return catId == 0 ?
                ResultFactory.buildSuccessResult(bookService.getAllBooks()) :
                ResultFactory.buildSuccessResult(bookService.getBooksByCatId(catId));
    }

    @GetMapping("/search/books/{keywords}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result searchBooks(@PathVariable String keywords) {
        return keywords.equals("") ?
                ResultFactory.buildCustomFailureResult(ResultCode.PARAM_IS_BLANK, "请输入关键字") :
                ResultFactory.buildSuccessResult(bookService.searchBooks(keywords));
    }
}
