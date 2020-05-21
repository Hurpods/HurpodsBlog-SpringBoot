package com.hurpods.springboot.hurpodsblog.service.impl;

import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.UploadService;
import com.hurpods.springboot.hurpodsblog.utils.MyUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Calendar;

@Service
public class UploadServiceImpl implements UploadService {
    private static final String BASE_FOLDER = "D:/Project/uploads/img";

    @Override
    public Result uploadContentPic(MultipartFile file) {
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
            return ResultFactory.buildSuccessResult("http://localhost:8090/file/pic/" + dateDirs + File.separator + descFile.getName());
        } catch (Exception e) {
            return ResultFactory.buildFailureResult(e.getMessage());
        }
    }

    @Override
    public Result uploadAvatar(MultipartFile file) {
        return null;
    }

    @Override
    public Result uploadCover(MultipartFile file) {
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        String suffix = filename.substring(filename.lastIndexOf("."));
        File descFile = new File(
                BASE_FOLDER
                        + File.separator
                        + "cover"
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
            return ResultFactory.buildSuccessResult("http://localhost:8090/file/cover/" + descFile.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResultFactory.buildFailureResult(e.getMessage());
        }
    }
}
