package com.teemlaren.RTFD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class RtfdApplication {

	public static void main(String[] args) {
		SpringApplication.run(RtfdApplication.class, args);
	}

}
