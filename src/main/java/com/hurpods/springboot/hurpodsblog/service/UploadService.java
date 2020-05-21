package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.result.Result;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    Result uploadContentPic(MultipartFile file);

    Result uploadAvatar(MultipartFile file);

    Result uploadCover(MultipartFile file);
}
