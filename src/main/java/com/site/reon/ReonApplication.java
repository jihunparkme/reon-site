package com.site.reon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ReonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReonApplication.class, args);
	}

}
