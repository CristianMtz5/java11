package com.example.reporteadorBackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ReporteadorBackEndApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(ReporteadorBackEndApplication.class);
	}

	public static void main(String[] args){
		SpringApplication.run(ReporteadorBackEndApplication.class, args);
	}
}
