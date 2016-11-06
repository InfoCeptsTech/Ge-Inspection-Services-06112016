package com.ge.inspection.ia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringBootOauth2Application extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(SpringBootOauth2Application.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootOauth2Application.class);
    }
    
}
