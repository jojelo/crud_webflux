package com.jojelo.api_rest_v1.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {
    private int id;
    private String title;
    private String slug;
    private double price;
    private String description;
    private CategoryResponseDTO category;
    private List<String> images;
    private String creationAt;
    private String updatedAt;
}
