package com.hurpods.springboot.hurpodsblog.dao;

import com.hurpods.springboot.hurpodsblog.pojo.CityUserRef;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityUserRefDAO {
    void insertCUR(CityUserRef cur);

    void updateCUR(CityUserRef cur);

    CityUserRef getCURByUserId(@Param("userId") Integer userId);
}
