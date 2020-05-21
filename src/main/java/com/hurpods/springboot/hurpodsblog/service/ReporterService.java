package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.dto.ReporterDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Reporter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReporterService {
    int insertReporter(ReporterDTO reporterDTO,String status);

    List<Reporter> getAllReporters();

    Reporter getReporterByBook(@Param("bookId") Integer bookId);
}
