package com.movie.discovery_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryServerApplication {

	private static final Logger logger = LoggerFactory.getLogger(DiscoveryServerApplication.class);

	public static void main(String[] args) {
		logger.info("Starting Discovery Server...");
		SpringApplication.run(DiscoveryServerApplication.class, args);
		logger.info("Discovery Server Started Successfully");
	}

}
