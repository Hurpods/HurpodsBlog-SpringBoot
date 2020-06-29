package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.dto.ReporterDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Article;
import com.hurpods.springboot.hurpodsblog.pojo.Reporter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentService {
    int insertReporter(ReporterDTO reporterDTO, String status);

    List<Reporter> getAllReporters();

    Reporter getReporterByBook(@Param("bookId") Integer bookId);

    List<Article> getAllArticle();

    Article getArticleById(@Param("id") Integer id);

    List<Object> getCarousel();

    Reporter getReporterById(Integer id);
}
