package com.Fintech.eWallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EWalletApplication {
	private static Logger logger = LoggerFactory.getLogger(EWalletApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EWalletApplication.class, args);
		logger.info("Hi, Your Main E-wallet application has been started successfully");
	}

}
