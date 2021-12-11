package com.antazri.servermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.antazri.servermanager")
@EnableWebMvc
@EnableJpaRepositories
public class DedicatedServerManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DedicatedServerManagerApplication.class, args);
    }

}
