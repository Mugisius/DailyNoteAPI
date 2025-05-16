package com.mugisius.DailyNoteApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.mugisius.DailyNoteApi")
public class DailyNoteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyNoteApiApplication.class, args);
	}

}
