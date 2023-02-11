package com.sportyshoe.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.sportyshoe.common.entity"})
public class MainBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainBackEndApplication.class, args);
    }

}