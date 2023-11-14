package com.healinghaven.bigmomma;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BigMommaApplication {

	private static final Logger LOG = LoggerFactory.getLogger(BigMommaApplication.class);

	public static void main(String[] args) {
		LOG.info("Starting application [BigMommaApplication] and hoping for the best :)");
		SpringApplication.run(BigMommaApplication.class, args);
	}

}
