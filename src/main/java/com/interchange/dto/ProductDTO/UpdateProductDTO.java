package com.interchange.dto.ProductDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProductDTO {
    @NotBlank(message = "Product name is required")
    private String proName;
    @NotBlank(message = "Product description is required")
    private String proDescription;
    @NotBlank(message = "Product color is required")
    private String proColor;
}
