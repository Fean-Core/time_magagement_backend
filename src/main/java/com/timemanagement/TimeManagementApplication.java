package com.timemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class TimeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeManagementApplication.class, args);
    }

}
