package com.hurpods.springboot.hurpodsblog.dto;

import lombok.Data;

@Data
public class ReporterDTO {
    private String title;
    private String content;
    private Float rate;
    private Integer book;
}
