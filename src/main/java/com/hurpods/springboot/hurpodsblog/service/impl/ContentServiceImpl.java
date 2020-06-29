package com.hurpods.springboot.hurpodsblog.service.impl;

import com.hurpods.springboot.hurpodsblog.dao.ContentDAO;
import com.hurpods.springboot.hurpodsblog.dto.ReporterDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Article;
import com.hurpods.springboot.hurpodsblog.pojo.Reporter;
import com.hurpods.springboot.hurpodsblog.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    ContentDAO contentDAO;

    @Override
    public int insertReporter(ReporterDTO reporterDTO, String status) {
        Reporter reporter = new Reporter(reporterDTO);
        Timestamp nowTime = new Timestamp(new Date().getTime());
        reporter.setPostTime(nowTime);

        switch (status) {
            case "save":
                reporter.setReporterStatus(0);
                break;
            case "post":
                reporter.setReporterStatus(1);
                break;
        }

        return contentDAO.insertReporter(reporter);
    }

    @Override
    public List<Reporter> getAllReporters() {
        return contentDAO.getAllReporters();
    }

    @Override
    public Reporter getReporterByBook(Integer bookId) {
        return contentDAO.getReporterByBook(bookId);
    }

    @Override
    public List<Article> getAllArticle() {
        return contentDAO.getAllArticle();
    }

    @Override
    public Article getArticleById(Integer id) {
        return contentDAO.getArticleById(id);
    }

    @Override
    public List<Object> getCarousel() {
        List<Article> articleList = contentDAO.getAllArticle();
        List<Reporter> reporterList = contentDAO.getAllReporters();
        List<Object> objectList = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            objectList.add(articleList.get(i));
            objectList.add(reporterList.get(i));
        }
        return objectList;
    }

    @Override
    public Reporter getReporterById(Integer id) {
        return contentDAO.getReporterById(id);
    }
}
