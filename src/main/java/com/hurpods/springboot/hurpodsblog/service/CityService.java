package com.hurpods.springboot.hurpodsblog.service;

import com.hurpods.springboot.hurpodsblog.pojo.Province;

import java.util.List;

public interface CityService {
    List<Province> getAllCity();

    List<String> getCityNameByCode(String provinceCode,String cityCode);
}
