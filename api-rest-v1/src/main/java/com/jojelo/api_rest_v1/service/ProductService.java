package com.jojelo.api_rest_v1.service;

import com.jojelo.api_rest_v1.model.dto.ProductResponseDTO;
import reactor.core.publisher.Flux;

public interface ProductService {

    Flux<ProductResponseDTO> getAllProducts();

}
