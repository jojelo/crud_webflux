package com.jojelo.api_rest_v1.controller;

import com.jojelo.api_rest_v1.apicaller.dto.ProductRequestDTO;
import com.jojelo.api_rest_v1.model.dto.ProductUserResponseDTO;
import com.jojelo.api_rest_v1.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

//    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<ProductResponseDTO> getAllProducts() {
//        return productService.getAllProducts()
//                .delayElements(Duration.ofMillis(1000));
//    }

    @GetMapping()
    public Flux<ProductUserResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<ProductUserResponseDTO>> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok);
//                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "count")
    public Mono<Long> countAllProducts() {
        return productService.countAllProducts();
    }

    @PostMapping(value = "/new-product")
    public Mono<ResponseEntity<ProductUserResponseDTO>> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return productService.createProduct(productRequestDTO)
                .map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res));
    }

    @DeleteMapping(value = "/delete/{id}")
    public Mono<Boolean> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
