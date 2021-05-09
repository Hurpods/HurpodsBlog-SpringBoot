package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.dto.ArticleDTO;
import com.hurpods.springboot.hurpodsblog.dto.ReporterDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Reporter;
import com.hurpods.springboot.hurpodsblog.result.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.SoundbankResource;

public interface UploadService {
    Result uploadContentPic(MultipartFile file);

    Result uploadAvatar(MultipartFile file);

    Result uploadReporter(ReporterDTO file);

    Result uploadBookCover(MultipartFile file);

    Result updateReporter(ReporterDTO dto, Reporter r);

    Result uploadArticle(ArticleDTO file);

    Result uploadArticleCover(MultipartFile file);
}
