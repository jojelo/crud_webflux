package com.jojelo.api_rest_v1.service;

import com.jojelo.api_rest_v1.apicaller.dto.ProductResponseDTO;
import com.jojelo.api_rest_v1.model.dto.ProductUserResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<ProductUserResponseDTO> getAllProducts();

    Mono<ProductUserResponseDTO> getProductById(Long id);

}
