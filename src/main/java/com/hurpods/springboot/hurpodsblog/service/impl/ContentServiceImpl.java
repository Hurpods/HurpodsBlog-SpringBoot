package com.hurpods.springboot.hurpodsblog.service.impl;

import cn.hutool.http.HtmlUtil;
import com.hurpods.springboot.hurpodsblog.dao.ContentDAO;
import com.hurpods.springboot.hurpodsblog.dto.ArticleDTO;
import com.hurpods.springboot.hurpodsblog.dto.ReporterDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Article;
import com.hurpods.springboot.hurpodsblog.pojo.Reporter;
import com.hurpods.springboot.hurpodsblog.pojo.User;
import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.ContentService;
import com.hurpods.springboot.hurpodsblog.service.UploadService;
import com.hurpods.springboot.hurpodsblog.service.UserService;
import com.hurpods.springboot.hurpodsblog.vo.UserVo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    ContentDAO contentDAO;
    @Autowired
    UploadService uploadService;
    @Autowired
    UserService userService;


    @Override
    public Result insertReporter(ReporterDTO reporterDTO) {
        Reporter reporter = new Reporter(reporterDTO);
        Timestamp nowTime = new Timestamp(new Date().getTime());
        reporter.setPostTime(nowTime);
        reporterDTO = (ReporterDTO) uploadService.uploadReporter(reporterDTO).getData();
        reporter.setReporterContent(reporterDTO.getContent());
        int num = contentDAO.insertReporter(reporter);
        return num == 0 ?
                ResultFactory.buildFailureResult("发生未知错误，请稍后重试") :
                ResultFactory.buildSuccessResult("发表成功");
    }

    @Override
    public int getReporterNumber() {
        return contentDAO.getReporterNumber();
    }

    @Override
    public List<Reporter> getAllReporters() throws IOException {
        List<Reporter> reporterList = contentDAO.getAllReporters();
        for (Reporter reporter : reporterList) {
            String content = FileUtils.readFileToString(new File(reporter.getReporterContent()), StandardCharsets.UTF_8);
            reporter.setReporterContent(HtmlUtil.unescape(content));
        }
        return reporterList;
    }

    @Override
    public Reporter getReporterByBook(Integer bookId) throws IOException {
        Reporter reporter = contentDAO.getReporterByBook(bookId);
        String content = FileUtils.readFileToString(new File(reporter.getReporterContent()), StandardCharsets.UTF_8);
        reporter.setReporterContent(HtmlUtil.unescape(content));
        return reporter;
    }

    @Override
    public List<Article> getAllArticle() throws IOException {
        List<Article> articleList = contentDAO.getAllArticle();
        for (Article article : articleList) {
            String content = FileUtils.readFileToString(new File(article.getArticleContent()), StandardCharsets.UTF_8);
            article.setArticleContent(HtmlUtil.unescape(content));
        }
        return articleList;
    }

    @Override
    public Article getArticleById(Integer id) throws IOException {
        Article article = contentDAO.getArticleById(id);
        String content = FileUtils.readFileToString(new File(article.getArticleContent()), StandardCharsets.UTF_8);
        article.setArticleContent(HtmlUtil.unescape(content));
        return article;
    }

    @Override
    public List<Object> getCarousel() throws IOException {
        List<Article> articleList = contentDAO.getAllArticle();
        for (Article article : articleList) {
            String content = FileUtils.readFileToString(new File(article.getArticleContent()), StandardCharsets.UTF_8);
            article.setArticleContent(HtmlUtil.unescape(content));
        }

        List<Reporter> reporterList = contentDAO.getAllReporters();
        for (Reporter reporter : reporterList) {
            String content = FileUtils.readFileToString(new File(reporter.getReporterContent()), StandardCharsets.UTF_8);
            reporter.setReporterContent(HtmlUtil.unescape(content));
        }

        List<Object> objectList = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            objectList.add(articleList.get(i));
            objectList.add(reporterList.get(i));
        }
        return objectList;
    }

    @Override
    public Reporter getReporterById(Integer id) throws IOException {
        Reporter reporter = contentDAO.getReporterById(id);
        String content = FileUtils.readFileToString(new File(reporter.getReporterContent()), StandardCharsets.UTF_8);
        reporter.setReporterContent(HtmlUtil.unescape(content));
        return reporter;
    }

    @Override
    public Result deleteReporterById(Integer id) {
        int num = contentDAO.deleteReporterById(id);
        return num == 1 ?
                ResultFactory.buildSuccessResult(null) :
                ResultFactory.buildFailureResult("未知错误");
    }

    @Override
    public Result updateReporter(ReporterDTO dto, Integer id) {
        Reporter r = contentDAO.getReporterById(id);
        Timestamp nowTime = new Timestamp(new Date().getTime());
        r.setPostTime(nowTime);
        r.setReporterTitle(dto.getTitle());
        r.setBookId(dto.getBook());
        r.setBookRate(dto.getRate());
        int num = 0;
        if (uploadService.updateReporter(dto, r).getCode() == 1) {
            num = contentDAO.updateReporter(r);
        }

        return num == 1 ?
                ResultFactory.buildSuccessResult(r) :
                ResultFactory.buildFailureResult("未知错误");
    }

    @Override
    public List<Reporter> reporterFuzzySearch(String keywords) {
        return contentDAO.reporterFuzzySearch(keywords);
    }

    @Override
    public Result insertArticle(ArticleDTO articleDTO) {
        Article article = new Article(articleDTO);
        Timestamp nowTime = new Timestamp(new Date().getTime());
        article.setArticleCreateTime(nowTime);
        article.setArticleUpdateTime(nowTime);
        article.setArticleCover(articleDTO.getCover());
        String sum = HtmlUtil.filter(articleDTO.getContent());
        if (sum.length() >= 40) {
            sum = sum.substring(40);
        }
        article.setArticleSummary(sum);

        User user = userService.getUserByUsername(articleDTO.getUserName());
        UserVo userVo=new UserVo(user);
        article.setUser(userVo);

        articleDTO = (ArticleDTO) uploadService.uploadArticle(articleDTO).getData();
        article.setArticleContent(articleDTO.getContent());

        int num = contentDAO.insertArticle(article);

        return num == 1 ?
                ResultFactory.buildSuccessResult("发表成功") :
                ResultFactory.buildFailureResult("发生错误");
    }

    @Override
    public int getArticleNumber() {
        return contentDAO.getArticleNumber();
    }

}
