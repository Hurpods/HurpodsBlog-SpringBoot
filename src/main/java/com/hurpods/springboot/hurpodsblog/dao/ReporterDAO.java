package com.hurpods.springboot.hurpodsblog.dao;


import com.hurpods.springboot.hurpodsblog.pojo.Reporter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporterDAO {
    int insertReporter(Reporter reporter);

    List<Reporter> getAllReporters();

    Reporter getReporterByBook(@Param("bookId") Integer bookId);
}
