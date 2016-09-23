package com.sapient.app.shop.org;

import javax.servlet.Filter;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FutureStoreServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FutureStoreServicesApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean requestDumperFilter() {
	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    Filter requestDumperFilter = new RequestDumperFilter();
	    registration.setFilter(requestDumperFilter);
	    registration.addUrlPatterns("/*");
	    return registration;
	}
}


