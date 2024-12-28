package com.songnguyen.example05.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class ContentConfig implements WebMvcConfigurer {
    @Override
    public void configureContentNegotiation(@SuppressWarnings("null") ContentNegotiationConfigurer configurer){
        configurer.favorParameter(true).parameterName("meidaType").defaultContentType(MediaType.APPLICATION_JSON)
        .mediaType("json", MediaType.APPLICATION_JSON).mediaType("xml", MediaType.APPLICATION_XML);
    }
}
