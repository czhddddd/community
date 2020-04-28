package com.czh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
public class communityApplication {
    public static void main(String[] args) {
        SpringApplication.run(communityApplication.class,args);
    }

}
