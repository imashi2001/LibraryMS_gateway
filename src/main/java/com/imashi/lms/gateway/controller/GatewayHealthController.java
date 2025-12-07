package com.imashi.lms.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GatewayHealthController {

	@GetMapping("/api/gateway/health")
	public Mono<String> health() {
		return Mono.just("Gateway is running on port 8080");
	}
}
