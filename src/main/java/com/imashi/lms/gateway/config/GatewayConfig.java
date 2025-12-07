package com.imashi.lms.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				// Route to backend service
				// All requests to /api/backend/** will be forwarded to http://localhost:8081
				// with /api/backend prefix stripped
				.route("backend-route", r -> r
						.path("/api/backend/**")
						.filters(f -> f.stripPrefix(2))
						.uri("http://localhost:8081"))
				.build();
	}
}
