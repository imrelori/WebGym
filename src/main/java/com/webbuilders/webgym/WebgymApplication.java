package com.webbuilders.webgym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WebgymApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebgymApplication.class, args);
    }
}
