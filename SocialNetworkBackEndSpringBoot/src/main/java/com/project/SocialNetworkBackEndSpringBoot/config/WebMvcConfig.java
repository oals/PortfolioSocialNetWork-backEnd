package com.project.SocialNetworkBackEndSpringBoot.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Log4j2
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${uploadPath}")
    String uploadPath;

    //uploadPath=file:///c:/SocialNetwork/Feed
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("변환");
        log.info(uploadPath);
        registry.addResourceHandler("/images/**") //리소스 읽어내기
                .addResourceLocations(uploadPath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }

}

