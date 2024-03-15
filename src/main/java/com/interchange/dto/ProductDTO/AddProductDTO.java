package com.interchange.dto.ProductDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Map;

@Data
public class AddProductDTO {
    @NotBlank(message = "The product name can not null")
    private String productName;
    @NotBlank(message = "The product description can not null")
    private String productDescription;
    @NotBlank(message = "The product color can not null")
    private String productColor;
    @Min(value = 1, message = "The measure unit Id must be greater than or equal to 0")
    private int measureUnitId;
    @Min(value = 0, message = "The product length must be greater than or equal to 0")
    private double length;
    @Min(value = 0, message = "The product height must be greater than or equal to 0")
    private double height;
    @Min(value = 0, message = "The product width must be greater than or equal to 0")
    private double width;
    @NotBlank(message = "The category name can not null")
    private String categoryName;
    @NotBlank(message = "The material name can not null")
    private String materialName;
    @NotBlank(message = "The room category can not null")
    private String roomCategoryName;
    @NotEmpty(message = "The unit prices map can not be null or empty")
    private Map<Integer, Double> unitPrices;
}
