package com.hurpods.springboot.hurpodsblog.dao;


import com.hurpods.springboot.hurpodsblog.dto.ReporterDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Article;
import com.hurpods.springboot.hurpodsblog.pojo.Reporter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentDAO {
    int getReporterNumber();

    int insertReporter(Reporter reporter);

    List<Reporter> getAllReporters();

    Reporter getReporterByBook(@Param("bookId") Integer bookId);

    List<Article> getAllArticle();

    Article getArticleById(@Param("id") Integer id);

    Reporter getReporterById(Integer id);

    int deleteReporterById(Integer id);

    int updateReporter(Reporter reporter);

    List<Reporter> reporterFuzzySearch(String keywords);

    int insertArticle(Article article);

    int getArticleNumber();
}
