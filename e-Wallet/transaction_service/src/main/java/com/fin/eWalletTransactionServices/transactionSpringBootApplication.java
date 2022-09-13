package com.fin.eWalletTransactionServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class transactionSpringBootApplication {
        private static Logger logger = LoggerFactory.getLogger(transactionSpringBootApplication.class);
         public static void main(String[] args){
            SpringApplication.run(transactionSpringBootApplication.class);
            logger.info("Transaction Application is started on port number 8003");

        }


}
