package com.example.spring_bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This annotation tells Spring Boot to:
// - Scan for components (controllers, services, repositories, etc.)
// - Set up autoconfiguration (based on your classpath and dependencies)
// - Mark this as the main entry point of your Spring Boot application
@SpringBootApplication
public class SpringBankApplication {

	// main method — it runs first when your app starts
	public static void main(String[] args) {

		// This launches the entire Spring Boot application:
		// - Starts the embedded server (like Tomcat)
		// - Initializes the database connection
		// - Starts listening on port 8080 (by default)
		SpringApplication.run(SpringBankApplication.class, args);
	}

}
