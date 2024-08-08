package com.phutl.meowpet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MeowpetApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeowpetApplication.class, args);
	}

}
