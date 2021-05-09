package com.hurpods.springboot.hurpodsblog.pojo;

import cn.hutool.http.HtmlUtil;
import com.hurpods.springboot.hurpodsblog.dto.ReporterDTO;
import com.hurpods.springboot.hurpodsblog.vo.UserVo;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Reporter {
    private Integer reporterId;
    private String reporterTitle;
    private String reporterContent;
    private Integer bookId;
    private Float bookRate;
    private Timestamp postTime;
    private Integer reporterStatus;
    private Book book;
    private UserVo user;

    public Reporter(ReporterDTO reporterDTO) {
        this.reporterTitle = HtmlUtil.escape(reporterDTO.getTitle());
        this.bookId = reporterDTO.getBook();
        this.reporterContent = HtmlUtil.escape(reporterDTO.getContent());
        this.bookRate = reporterDTO.getRate();
    }
}
