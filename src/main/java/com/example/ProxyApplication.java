package com.example;

import com.example.filters.post.LocationHeaderRewritingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

	@Bean
	public LocationHeaderRewritingFilter headerUrlRewritingFilter(RouteLocator routeLocator) {
		return new LocationHeaderRewritingFilter(routeLocator);
	}
}
