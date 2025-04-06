package com.jojelo.api_rest_v1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojelo.api_rest_v1.apicaller.ApiDataCaller;
import com.jojelo.api_rest_v1.apicaller.dto.ProductRequestDTO;
import com.jojelo.api_rest_v1.mapper.ProductMapper;
import com.jojelo.api_rest_v1.model.dto.ProductUserResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ApiDataCaller apiDataCaller;
    private final ProductMapper productMapper;

    @Override
    public Flux<ProductUserResponseDTO> getAllProducts() {
        return apiDataCaller.getAllProducts()
                .map(productMapper::toUserResponseDTO);
    }

    @Override
    public Mono<ProductUserResponseDTO> getProductById(Long id) {
        var res = apiDataCaller.getProductById(id)
                .map(productMapper::toUserResponseDTO)
                .onErrorResume(WebClientResponseException.BadRequest.class, ex -> {
                    log.error("Error retrieving product with id {}: {}", id, ex.getMessage());
//                    return Mono.error(new RuntimeException("Producto no encontrado"));
                    return Mono.empty();
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("No se encontro producto con id {}", id);
                    return Mono.empty();
                }));
        return res;
    }

    @Override
    public Mono<Long> countAllProducts() {
        return apiDataCaller.getAllProducts()
                .count()
                .doOnSuccess(count -> {
                    if (count == 0) {
                        log.info("No se encontraron productos");
                    } else {
                        log.info("Total de productos: {}", count);
                    }
                });
    }

    @Override
    public Mono<ProductUserResponseDTO> createProduct(ProductRequestDTO productRequestDTO) {
        return apiDataCaller.createProduct(productRequestDTO)
                .map(productMapper::toUserResponseDTO)
                .doOnSuccess(product -> log.info("Producto creado: {}", product.getTitle()))
                .doOnError(error -> log.error("Error creando producto", error))
                .onErrorResume(WebClientResponseException.BadRequest.class, ex -> {
                    try {
                        String responseBody = ex.getResponseBodyAsString();
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode errorNode = mapper.readTree(responseBody);

                        String errorName = errorNode.path("name").asText("");
                        String errorMessage = errorNode.path("message").asText("");

                        if ("EntityNotFoundError".equals(errorName) && errorMessage.contains("Category")) {
                            return Mono.error(new RuntimeException("La categoría especificada no existe"));
                        } else {
                            return Mono.error(new RuntimeException("Error de validación: " + errorMessage));
                        }
                    } catch (Exception e) {
                        log.error("Error al procesar la respuesta de error", e);
                        return Mono.error(new RuntimeException("Error al validar el producto"));
                    }
                });
    }

    @Override
    public Mono<Boolean> deleteProduct(Long id) {
        return apiDataCaller.deleteProduct(id)
                .doOnSuccess(aVoid -> log.info("Producto eliminado con id {}", id))
                .doOnError(error -> log.error("Error eliminando producto", error))
                .onErrorResume(WebClientResponseException.BadRequest.class, ex -> {
                    log.error("Error eliminando producto con id {}: {}", id, ex.getMessage());
                    return Mono.error(new RuntimeException("Error eliminando producto"));
                });
    }
}
