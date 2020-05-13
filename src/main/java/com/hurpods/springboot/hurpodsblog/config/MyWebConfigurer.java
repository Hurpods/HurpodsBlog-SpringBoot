package com.hurpods.springboot.hurpodsblog.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//                .addResourceHandler("/api/file/**")
//                .addResourceLocations(
//                        "file:"
//                                + "D:"
//                                + File.separator
//                                + "Project"
//                                + File.separator
//                                + "learning"
//                                + File.separator
//                                + "vue_springboot"
//                                + File.separator
//                                + "src"
//                                + File.separator
//                                + "assets"
//                                + File.separator
//                                + "img"
//                                + File.separator
//                );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //所有请求都允许跨域，使用这种配置方法就不能在 interceptor 中再配置 header 了
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*")
                .maxAge(3600);
    }

}
