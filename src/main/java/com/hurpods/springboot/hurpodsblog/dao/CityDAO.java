package com.hurpods.springboot.hurpodsblog.dao;

import com.hurpods.springboot.hurpodsblog.pojo.Province;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityDAO {
    List<Province> getAllCity();
}
