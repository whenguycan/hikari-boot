package com.lepus.hikariboot;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@ServletComponentScan
@SpringBootApplication
@EnableAutoConfiguration
public class HikariBootApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(HikariBootApplication.class, args);
		context.getId();
	}
	
	@Bean
	public SessionFactory sessionFactory(EntityManagerFactory emf) {
		return emf.unwrap(SessionFactory.class);
	}
	
}
