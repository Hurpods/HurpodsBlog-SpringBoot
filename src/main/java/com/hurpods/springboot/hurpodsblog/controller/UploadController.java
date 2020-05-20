package com.hurpods.springboot.hurpodsblog.controller;

import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.utils.MyUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/upload")
public class UploadController {
    private static final String BASE_FOLDER = "D:/Project/uploads/img";

    @PostMapping("/image")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Map<String, Object> upload(@RequestParam("upload") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));

        Calendar date = Calendar.getInstance();
        File dateDirs = new File(date.get(Calendar.YEAR) + File.separator + (date.get(Calendar.MONTH) + 1));
        File descFile = new File(
                BASE_FOLDER
                        + File.separator
                        + "contentPic"
                        + File.separator
                        + dateDirs
                        + File.separator
                        + MyUtil.getRandomString(16)
                        + suffix);
        if (!descFile.getParentFile().exists()) {
            descFile.getParentFile().mkdirs();
        }

        while (descFile.exists()) {
            descFile.delete();
        }
        try {
            file.transferTo(descFile);
            System.out.println(descFile.getPath());
            map.put("uploaded", true);
            map.put("url", "http://localhost:8090/file/pic/" + dateDirs + File.separator + descFile.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            map.put("uploaded", false);
            map.put("url", "/");
        }
        return map;
    }
}
