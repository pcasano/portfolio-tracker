package org.pcasano.portfoliotracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class PortfolioTrackerApplication {

	public static void main(String[] args) {
		//SpringApplication.run(PortfolioTrackerApplication.class, args);
		SpringApplication app = new SpringApplication(PortfolioTrackerApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", "8088"));
		app.run(args);
	}

}
