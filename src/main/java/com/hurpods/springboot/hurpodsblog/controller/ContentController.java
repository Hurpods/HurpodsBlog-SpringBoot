package com.hurpods.springboot.hurpodsblog.controller;

import com.hurpods.springboot.hurpodsblog.dto.ReporterDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Book;
import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultCode;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.BookService;
import com.hurpods.springboot.hurpodsblog.service.ReporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/content")
public class ContentController {
    @Autowired
    BookService bookService;

    @Autowired
    ReporterService reporterService;

    @GetMapping("/books")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result getAllBooks() {
        return ResultFactory.buildSuccessResult(bookService.getAllBooks());
    }

    @PostMapping("/books")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result updateOrInsertBook(@RequestBody Book book) {
        System.out.println(book);
        return book.getBookId() == null ? bookService.insertBook(book) : bookService.updateBook(book);
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

    @GetMapping("/books/search/{keywords}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result searchBooks(@PathVariable String keywords) {
        return keywords.equals("") ?
                ResultFactory.buildCustomFailureResult(ResultCode.PARAM_IS_BLANK, "请输入关键字") :
                ResultFactory.buildSuccessResult(bookService.searchBooks(keywords));
    }

    @PostMapping("/reporter/write/{status}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result writeReporter(@RequestBody ReporterDTO reporterDTO, @PathVariable String status) {
        System.out.println(reporterDTO);
        int num = reporterService.insertReporter(reporterDTO, status);
        return num == 0 ?
                ResultFactory.buildFailureResult("发生未知错误，请稍后重试") :
                ResultFactory.buildSuccessResult("发表成功");
    }

    @GetMapping("/reporter/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_EDITOR','ROLE_JUDGEMENT','ROLE_MANAGER')")
    public Result getReporter(@PathVariable Integer id) {
        return ResultFactory.buildSuccessResult(reporterService.getReporterByBook(id));
    }

}
