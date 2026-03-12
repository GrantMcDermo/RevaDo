package com.revature.RevaDo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /*
    Given I'm using a custom MVC interceptor rather than Spring Security sessions I've set up the filter chain mainly to
    allow CORS preflight requests, disable CSRF since the app is stateless, and avoid blocking requests at the security
    filter layer while my interceptor handles authorization.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/login", "/user").permitAll()
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
