package com.hurpods.springboot.hurpodsblog.service.impl;

import com.hurpods.springboot.hurpodsblog.dao.CityDAO;
import com.hurpods.springboot.hurpodsblog.pojo.Province;
import com.hurpods.springboot.hurpodsblog.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    CityDAO cityDAO;

    @Override
    public List<Province> getAllCity() {
        return cityDAO.getAllCity();
    }
}
