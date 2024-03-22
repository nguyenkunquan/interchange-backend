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
    @Min(value = 1, message = "The category Id must be greater than or equal to 0")
    private int categoryId;
    @Min(value = 1, message = "The material Id must be greater than or equal to 0")
    private int materialId;
    @Min(value = 1, message = "The room category Id must be greater than or equal to 0")
    private int roomCategoryId;
    @NotEmpty(message = "The unit prices map can not be null or empty")
    private Map<Integer, Double> unitPrices;
    public boolean isValidUnitPrices() {
        for (Double value : unitPrices.values()) {
            if (value <= 0) {
                return false;
            }
        }
        return true;
    }
}
