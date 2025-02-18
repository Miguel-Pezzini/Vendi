package com.vendi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VendiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendiApplication.class, args);
	}

}
