package com.my.ut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JacocoApplication {

    public static void main(String[] args){

        System.out.println("JacocoApplication init before.....");

        SpringApplication.run(JacocoApplication.class, args);

        System.out.println("JacocoApplication init after.....");
    }

}
