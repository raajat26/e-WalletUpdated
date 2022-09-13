package com.fin.ewalletUserServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserSpringBootApplication {
    private static Logger logger = LoggerFactory.getLogger(UserSpringBootApplication.class);

    public static void main(String[] args){
        SpringApplication.run(UserSpringBootApplication.class, args);
        logger.info("Hi, Your User Services Application has been started successfully on port number 8002");
    }
}
