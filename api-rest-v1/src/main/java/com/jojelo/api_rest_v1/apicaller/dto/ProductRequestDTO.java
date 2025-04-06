package com.jojelo.api_rest_v1.apicaller.dto;

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
public class ProductRequestDTO {
    private String title;
    private double price;
    private String description;
    private Integer categoryId;
    private List<String> images;
}
