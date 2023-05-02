package com.greenThumb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GreenThumbApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenThumbApplication.class, args);
	}

}
