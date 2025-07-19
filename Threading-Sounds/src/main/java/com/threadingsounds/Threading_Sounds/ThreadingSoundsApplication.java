package com.threadingsounds.Threading_Sounds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ThreadingSoundsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThreadingSoundsApplication.class, args);
	}

	@GetMapping("/hello")
    public String helloworld() {
        return "Hello world";
	}
}

