package com.deliverytouroptimizer;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class DeliveryTourOptimizerApplication {

	public static void main(String[] args) {
		loadEnv();
		SpringApplication.run(DeliveryTourOptimizerApplication.class, args);
	}

	private static void loadEnv(){
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});
	}
}
