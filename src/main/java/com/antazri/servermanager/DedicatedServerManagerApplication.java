package com.antazri.servermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.antazri.servermanager")
public class DedicatedServerManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DedicatedServerManagerApplication.class, args);
    }

}
