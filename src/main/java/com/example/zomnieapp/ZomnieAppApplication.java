package com.example.zomnieapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZomnieAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZomnieAppApplication.class, args);
    }

}
