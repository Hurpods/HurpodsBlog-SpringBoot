package com.hurpods.springboot.hurpodsblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hurpods.springboot.hurpodsblog.dao")
public class HurpodsblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(HurpodsblogApplication.class, args);
    }

}
