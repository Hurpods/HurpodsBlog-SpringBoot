package com.hurpods.springboot.hurpodsblog.controller;

import com.hurpods.springboot.hurpodsblog.result.Result;
import com.hurpods.springboot.hurpodsblog.result.ResultCode;
import com.hurpods.springboot.hurpodsblog.result.ResultFactory;
import org.apache.commons.io.FileUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/backStage")
public class BackStageController {


    @GetMapping("/logs/{date}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result readLogs(@PathVariable String date) throws IOException {
        String path = "D:\\Development\\uploads\\logs\\" + date;
        String content = FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
        content = content.replaceAll("\n", "<br/>");
        return ResultFactory.buildSuccessResult(content);
    }

    @GetMapping("/logs")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Result getAllLogs() throws IOException {
        String path = "D:\\Development\\uploads\\logs\\";
        List<String> logList = new ArrayList<>();
        File logDir = new File(path);

        File[] tempList = logDir.listFiles();
        if (tempList != null) {
            for (File file : tempList) {
                String date = file.getName();
                logList.add(date);
            }
            return ResultFactory.buildSuccessResult(logList);
        }
        return ResultFactory.buildFailureResult(ResultCode.RESULT_DATA_NONE);
    }
}
