package com.jojelo.api_rest_v1.apicaller;

import com.jojelo.api_rest_v1.config.ApplicationProperties;
import com.jojelo.api_rest_v1.model.dto.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiDataCaller {

    private final WebClient.Builder webClientBuilder;
    private final ApplicationProperties applicationProperties;

    public Flux<ProductResponseDTO> getAllProducts() {
        return webClientBuilder.baseUrl(applicationProperties.getBaseUrl())
                .build()
                .get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(ProductResponseDTO.class)
                .doOnNext(product -> log.info("Product retrieved: {}", product))
                .doOnError(error -> log.error("Error retrieving products", error))
                .doOnComplete(() -> log.info("All products retrieved"));
    }
}
