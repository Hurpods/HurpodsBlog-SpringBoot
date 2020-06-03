package com.hurpods.springboot.hurpodsblog.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CityUserRef implements Serializable {
    private static final long serialVersionUID = -7863725143155090575L;
    private Integer id;
    private Integer userId;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
}
