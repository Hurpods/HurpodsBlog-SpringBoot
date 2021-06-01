package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.dto.ArticleDTO;
import com.hurpods.springboot.hurpodsblog.dto.ReporterDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Article;
import com.hurpods.springboot.hurpodsblog.pojo.Reporter;
import com.hurpods.springboot.hurpodsblog.result.Result;
import org.springframework.web.multipart.MultipartFile;


public interface UploadService {
    Result uploadContentPic(MultipartFile file);

    Result uploadAvatar(int id ,MultipartFile file);

    Result uploadReporter(ReporterDTO file);

    Result uploadCover(MultipartFile file);

    Result updateReporter(ReporterDTO dto, Reporter r);

    Result uploadArticle(ArticleDTO file);

    Result updateArticle(ArticleDTO articleDTO, Article a);
}
