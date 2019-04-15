package com.mijnqiendatabase.qiendatabase;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mijnqiendatabase.qiendatabase.service.UserService;

@SpringBootApplication
public class QiendatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(QiendatabaseApplication.class, args);
	}
	@Bean
    CommandLineRunner init(UserService userService) {
        return (args) -> userService.initUsers();
    }
}
