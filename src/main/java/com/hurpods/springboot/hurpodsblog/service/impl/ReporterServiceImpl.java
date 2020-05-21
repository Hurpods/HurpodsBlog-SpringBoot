package com.hurpods.springboot.hurpodsblog.service.impl;

import com.hurpods.springboot.hurpodsblog.dao.ReporterDAO;
import com.hurpods.springboot.hurpodsblog.dto.ReporterDTO;
import com.hurpods.springboot.hurpodsblog.pojo.Reporter;
import com.hurpods.springboot.hurpodsblog.service.ReporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class ReporterServiceImpl implements ReporterService {
    @Autowired
    ReporterDAO reporterDAO;

    @Override
    public int insertReporter(ReporterDTO reporterDTO, String status) {
        Reporter reporter = new Reporter(reporterDTO);
        Timestamp nowTime = new Timestamp(new Date().getTime());
        reporter.setPostTime(nowTime);

        switch (status) {
            case "save":
                reporter.setReporterStatus(0);
                break;
            case "post":
                reporter.setReporterStatus(1);
                break;
        }

        return reporterDAO.insertReporter(reporter);
    }

    @Override
    public List<Reporter> getAllReporters() {
        return reporterDAO.getAllReporters();
    }

    @Override
    public Reporter getReporterByBook(Integer bookId) {
        return reporterDAO.getReporterByBook(bookId);
    }
}
