package com.hurpods.springboot.hurpodsblog.service.impl;

import com.hurpods.springboot.hurpodsblog.dao.CityDAO;
import com.hurpods.springboot.hurpodsblog.pojo.City;
import com.hurpods.springboot.hurpodsblog.pojo.Province;
import com.hurpods.springboot.hurpodsblog.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    CityDAO cityDAO;

    @Override
    public List<Province> getAllCity() {
        return cityDAO.getAllCity();
    }

    @Override
    public List<String> getCityNameByCode(String provinceCode, String cityCode) {
        Province province = cityDAO.getCityByCode(provinceCode);
        List<String> cityName = new ArrayList<>();

        cityName.add(province.getName());

        for (City city : province.getCityList()) {
            if (city.getCode().equals(cityCode)) {
                cityName.add(city.getName());
            }
        }

        return cityName;
    }
}
