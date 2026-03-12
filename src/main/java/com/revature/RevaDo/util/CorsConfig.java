package com.revature.RevaDo.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    /*
    We apply the CORS rules to all over our endpoints by mapping with the pattern /**.
     To make sure the front-end isn't blocked off, we allow requests from localhost:4200.
     I also specify what method types are allowed to come through from that port. I include OPTIONS because the browser
     sends preflight requests before some API calls to verify that it is allowed. Any header is allowed to come through
     so we can send the JWT token.

     */
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:5173")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
