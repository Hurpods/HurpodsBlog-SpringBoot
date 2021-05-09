package com.hurpods.springboot.hurpodsblog.service.impl;

import cn.hutool.http.HtmlUtil;
import com.hurpods.springboot.hurpodsblog.dto.ArticleDTO;
import com.hurpods.springboot.hurpodsblog.dto.ReporterDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Reporter;
import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import com.hurpods.springboot.hurpodsblog.service.UploadService;
import com.hurpods.springboot.hurpodsblog.utils.MyUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

@Service
public class UploadServiceImpl implements UploadService {
    private static final String BASE_FOLDER = "D:/Development/uploads";

    @Override
    public Result uploadContentPic(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        Calendar date = Calendar.getInstance();
        File dateDirs = new File(date.get(Calendar.YEAR) + File.separator + (date.get(Calendar.MONTH) + 1));
        File descFile = new File(
                BASE_FOLDER
                        + File.separator
                        + "img"
                        + File.separator
                        + "contentPic"
                        + File.separator
                        + dateDirs
                        + File.separator
                        + MyUtil.getRandomString(16)
                        + suffix
        );
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
    public Result uploadReporter(ReporterDTO file) {
        try {
            String fileName = MyUtil.getRandomString(24);
            String path = BASE_FOLDER
                    + File.separator
                    + "text"
                    + File.separator
                    + fileName;
            file.setContent(HtmlUtil.escape(file.getContent()));
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write(file.getContent());
            file.setContent("D:/Development/uploads/text/" + fileName);
            out.close();
            return ResultFactory.buildSuccessResult(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultFactory.buildFailureResult("IOException");
    }

    @Override
    public Result uploadBookCover(MultipartFile file) {
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        String suffix = filename.substring(filename.lastIndexOf("."));
        File descFile = new File(
                BASE_FOLDER
                        + File.separator
                        + "img"
                        + File.separator
                        + "cover"
                        + File.separator
                        + MyUtil.getRandomString(16)
                        + suffix
        );
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

    @Override
    public Result updateReporter(ReporterDTO dto, Reporter r) {
        try{
            String path = r.getReporterContent();
            BufferedWriter bw=new BufferedWriter(new FileWriter(path));
            bw.write(dto.getContent());
            bw.close();
            return ResultFactory.buildSuccessResult("success");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultFactory.buildSuccessResult("未知错误");
    }

    @Override
    public Result uploadArticle(ArticleDTO file) {
        try {
            String fileName = MyUtil.getRandomString(24);
            String path = BASE_FOLDER
                    + File.separator
                    + "text"
                    + File.separator
                    + fileName;
            file.setContent(HtmlUtil.escape(file.getContent()));
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write(file.getContent());
            file.setContent("D:/Development/uploads/text/" + fileName);
            out.close();
            return ResultFactory.buildSuccessResult(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultFactory.buildFailureResult("IOException");
    }

    @Override
    public Result uploadArticleCover(MultipartFile file) {
        return null;
    }
}
