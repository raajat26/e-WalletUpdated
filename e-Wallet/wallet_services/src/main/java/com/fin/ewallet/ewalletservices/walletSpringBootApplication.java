package com.fin.ewallet.ewalletservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class walletSpringBootApplication {
    private static Logger logger = LoggerFactory.getLogger(walletSpringBootApplication.class);
    public static void main(String[] args){
        SpringApplication.run(walletSpringBootApplication.class, args);
        logger.info("Hi, Your Wallet Application has been started successfully on port 8001");
    }
}
