package com.lpnote.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class FootstrucksDemoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FootstrucksDemoApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(FootstrucksDemoApplication.class, args);
    }
}
