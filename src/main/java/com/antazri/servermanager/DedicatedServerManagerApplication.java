package com.antazri.servermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.antazri.servermanager")
@EnableWebSecurity
@EnableWebMvc
@EnableJpaRepositories
@EnableMongoRepositories
public class DedicatedServerManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DedicatedServerManagerApplication.class, args);
    }

}
