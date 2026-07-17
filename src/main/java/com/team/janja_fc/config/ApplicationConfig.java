package com.team.janja_fc.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.team.janja_fc.security.JwtProperties;

@Configuration
@EnableConfigurationProperties({
        AdminProperties.class,
        JwtProperties.class
})
public class ApplicationConfig {

}