package com.hurpods.springboot.hurpodsblog.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {
    private static final String BASE_PATH = "D:" + File.separator + "Development" + File.separator + "uploads" + File.separator;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/file/pic/**")
                .addResourceLocations(
                        "file:"
                                + BASE_PATH
                                + "img"
                                + File.separator
                                + "contentPic"
                                + File.separator
                );
        registry.addResourceHandler("/file/avatar/**")
                .addResourceLocations(
                        "file:"
                                + BASE_PATH
                                + "img"
                                + File.separator
                                + "avatar"
                                + File.separator
                );
        registry.addResourceHandler("/file/cover/**")
                .addResourceLocations(
                        "file:"
                                + BASE_PATH
                                + "img"
                                + File.separator
                                + "cover"
                                + File.separator
                );
        registry.addResourceHandler("/file/text/**")
                .addResourceLocations(
                        "file:"
                                + BASE_PATH
                                + "text"
                                + File.separator
                );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .exposedHeaders("Authorization")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600);
    }

}
