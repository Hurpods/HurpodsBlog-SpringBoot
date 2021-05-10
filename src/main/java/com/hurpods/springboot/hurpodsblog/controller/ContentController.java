package com.hurpods.springboot.hurpodsblog.controller;

import com.github.pagehelper.PageHelper;
import com.hurpods.springboot.hurpodsblog.dto.ArticleDTO;
import com.hurpods.springboot.hurpodsblog.dto.ReporterDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Article;
import com.hurpods.springboot.hurpodsblog.pojo.Reporter;
import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultCode;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.BookService;
import com.hurpods.springboot.hurpodsblog.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    BookService bookService;

    @Autowired
    ContentService contentService;

    @GetMapping("/reporter/{id}")
    public Result getReporterByBook(@PathVariable Integer id) throws IOException {
        return ResultFactory.buildSuccessResult(contentService.getReporterById(id));
    }

    @PostMapping("/reporter")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result writeReporter(@RequestBody ReporterDTO reporterDTO) {
        return contentService.insertReporter(reporterDTO);
    }

    @GetMapping("/reporter")
    public Result getAllReporter(Integer pageNum) throws IOException {
        List<Reporter> reporterList;
        if (pageNum != null) {
            PageHelper.startPage(pageNum, 12);
        }

        reporterList = contentService.getAllReporters();
        Integer size = contentService.getReporterNumber();

        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", size);
        map.put("reporterList", reporterList);

        return reporterList.size() != 0 ?
                ResultFactory.buildSuccessResult(map) :
                ResultFactory.buildCustomFailureResult(ResultCode.RESULT_DATA_NONE, "无数据");
    }

    @DeleteMapping("/reporter/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result deleteReporter(@PathVariable Integer id) {
        return contentService.deleteReporterById(id);
    }

    @PutMapping("/reporter/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result updateReporter(@RequestBody ReporterDTO reporterDTO, @PathVariable Integer id) {
        return contentService.updateReporter(reporterDTO, id);
    }

    @GetMapping("/reporter/search/{keywords}")
    public Result reporterFuzzySearch(@PathVariable String keywords) {
        List<Reporter> reporterList = contentService.reporterFuzzySearch(keywords);
        return reporterList.size() != 0 ?
                ResultFactory.buildSuccessResult(reporterList) :
                ResultFactory.buildFailureResult(ResultCode.RESULT_DATA_NONE);
    }

    @GetMapping("/article/{id}")
    public Result getArticleById(@PathVariable Integer id) throws IOException {
        return ResultFactory.buildSuccessResult(contentService.getArticleById(id));
    }


    @GetMapping("/carousel")
    public Result getCarousel() throws IOException {
        return ResultFactory.buildSuccessResult(contentService.getCarousel());
    }

    @PostMapping("/article")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EDITOR')")
    public Result writeArticle(@RequestBody ArticleDTO articleDTO) {
        return contentService.insertArticle(articleDTO);
    }

    @GetMapping("/article")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EDITOR')")
    public Result getAllArticle(Integer pageNum) throws IOException {
        List<Article> articleList;
        if (pageNum != null) {
            PageHelper.startPage(pageNum, 12);
        }

        articleList = contentService.getAllArticle();
        Integer size = contentService.getArticleNumber();

        Map<String, Object> map = new HashMap<>();
        map.put("articleList", articleList);
        map.put("articleSize", size);

        return articleList.size() != 0 ?
                ResultFactory.buildSuccessResult(map) :
                ResultFactory.buildCustomFailureResult(ResultCode.RESULT_DATA_NONE, "无数据");
    }

    @GetMapping("/articles")
    public Result getHomeArticle() throws IOException {
        List<Article> articleList = contentService.getAllArticle();
        return articleList.size() != 0 ?
                ResultFactory.buildSuccessResult(articleList) :
                ResultFactory.buildCustomFailureResult(ResultCode.RESULT_DATA_NONE, "无数据");
    }

    @PutMapping("/article/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EDITOR')")
    public Result updateArticle(@RequestBody ArticleDTO articleDTO, @PathVariable Integer id) {
        return contentService.updateArticle(articleDTO,id);
    }

    @DeleteMapping("/article/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EDITOR')")
    public Result deleteArticle( @PathVariable Integer id) {
        return contentService.deleteArticleById(id);
    }
}
