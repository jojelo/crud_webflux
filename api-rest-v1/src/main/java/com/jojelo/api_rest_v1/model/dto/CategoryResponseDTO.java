package com.jojelo.api_rest_v1.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDTO {
    private int id;
    private String name;
    private String slug;
    private String image;
    private String creationAt;
    private String updatedAt;
}
