package com.jojelo.api_rest_v1.apicaller;

import com.jojelo.api_rest_v1.apicaller.dto.ProductRequestDTO;
import com.jojelo.api_rest_v1.config.ApplicationProperties;
import com.jojelo.api_rest_v1.apicaller.dto.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
                .doOnNext(product -> log.info("Products retrieved: {}", product))
                .doOnError(error -> log.error("Error retrieving products", error))
                .doOnComplete(() -> log.info("All products retrieved"));
    }

    public Mono<ProductResponseDTO> getProductById(Long id) {
        return webClientBuilder.baseUrl(applicationProperties.getBaseUrl())
                .build()
                .get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(ProductResponseDTO.class)
                .doOnSuccess(product -> log.info("Product retrieval completed"))
                .doOnError(error -> log.error("Error retrieving products", error));
    }

    public Mono<ProductResponseDTO> createProduct(ProductRequestDTO productRequestDTO) {
        return webClientBuilder.baseUrl(applicationProperties.getBaseUrl())
                .build()
                .post()
                .uri("/products")
                .bodyValue(productRequestDTO)
                .retrieve()
                .bodyToMono(ProductResponseDTO.class)
                .doOnSuccess(product -> log.info("Producto creado con éxito: {}", product))
                .doOnError(error -> log.error("Error al crear el producto {}", error.getMessage()));
    }

    public Mono<Boolean> deleteProduct(Long id) {
        return webClientBuilder.baseUrl(applicationProperties.getBaseUrl())
                .build()
                .delete()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .thenReturn(true)
                .doOnSuccess(v -> log.info("Producto con ID {} eliminado con éxito", id))
                .doOnError(error -> log.error("Error al eliminar el producto con ID {}: {}", id, error.getMessage()));
    }
}
