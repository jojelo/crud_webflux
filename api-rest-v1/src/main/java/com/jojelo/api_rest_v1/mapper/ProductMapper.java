package com.jojelo.api_rest_v1.mapper;

import com.jojelo.api_rest_v1.apicaller.dto.CategoryResponseDTO;
import com.jojelo.api_rest_v1.apicaller.dto.ProductResponseDTO;
import com.jojelo.api_rest_v1.model.dto.CategoryUserResponseDTO;
import com.jojelo.api_rest_v1.model.dto.ProductUserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductUserResponseDTO toUserResponseDTO(ProductResponseDTO productResponseDTO) {
        return ProductUserResponseDTO.builder()
                .title(productResponseDTO.getTitle())
                .slug(productResponseDTO.getSlug())
                .price(productResponseDTO.getPrice())
                .description(productResponseDTO.getDescription())
                .category(CategoryUserResponseDTO.builder()
                        .name(productResponseDTO.getCategory().getName())
                        .slug(productResponseDTO.getCategory().getSlug())
                        .image(productResponseDTO.getCategory().getImage())
                        .build())
                .images(productResponseDTO.getImages())
                .build();
    }
}
