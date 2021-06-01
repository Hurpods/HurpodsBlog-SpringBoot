package com.hurpods.springboot.hurpodsblog.controller;

import cn.hutool.json.JSONObject;
import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/upload")
public class UploadController {
    private static final String BASE_FOLDER = "D:/Project/uploads/img";
    @Autowired
    UploadService uploadService;

    @PostMapping("/content/image")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EDITOR')")
    public Map<String, Object> uploadContentImage(@RequestParam("upload") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        Result result = uploadService.uploadContentPic(file);

        switch (result.getCode()) {
            case 1:
                map.put("url", result.getData());
                map.put("uploaded", true);
                break;
            case 0:
                map.put("url", "/");
                map.put("uploaded", false);
                break;
        }

        return map;
    }

    @PostMapping("/cover")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result uploadCover(MultipartFile file) {
        return uploadService.uploadCover(file);
    }

    @PostMapping("/avatar")
    public Result uploadAvatar(@RequestParam(value = "id", required = false) String userId, @RequestParam(value = "file", required = false) MultipartFile file) {
        int id = Integer.parseInt(userId);
        return uploadService.uploadAvatar(id, file);
    }
}
