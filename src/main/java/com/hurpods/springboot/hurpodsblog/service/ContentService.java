package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.dto.ArticleDTO;
import com.hurpods.springboot.hurpodsblog.dto.ReporterDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Article;
import com.hurpods.springboot.hurpodsblog.pojo.Reporter;
import com.hurpods.springboot.hurpodsblog.result.Result;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;

public interface ContentService {
    Result insertReporter(ReporterDTO reporterDTO);

    int getReporterNumber();

    List<Reporter> getAllReporters() throws IOException;

    Reporter getReporterByBook(@Param("bookId") Integer bookId) throws IOException;

    List<Article> getAllArticle() throws IOException;

    Article getArticleById(@Param("id") Integer id) throws IOException;

    List<Object> getCarousel() throws IOException;

    Reporter getReporterById(Integer id) throws IOException;

    Result deleteReporterById(@Param("reporterId")Integer id);

    Result updateReporter(ReporterDTO dto,Integer id);

    List<Reporter> reporterFuzzySearch(String keywords);

    Result insertArticle(ArticleDTO articleDTO);

    int getArticleNumber();
}
