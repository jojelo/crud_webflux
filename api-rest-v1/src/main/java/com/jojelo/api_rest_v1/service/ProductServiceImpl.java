package com.jojelo.api_rest_v1.service;

import com.jojelo.api_rest_v1.apicaller.ApiDataCaller;
import com.jojelo.api_rest_v1.model.dto.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ApiDataCaller apiDataCaller;

    @Override
    public Flux<ProductResponseDTO> getAllProducts() {
        Flux<ProductResponseDTO> productResponseDTOFlux = apiDataCaller.getAllProducts();
        return productResponseDTOFlux;
    }
}
