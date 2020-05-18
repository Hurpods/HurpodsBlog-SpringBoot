package com.hurpods.springboot.hurpodsblog.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Province implements Serializable {
    private static final long serialVersionUID = 3098756495793055919L;
    String id;
    String code;
    String name;
    List<City> cityList;
}
