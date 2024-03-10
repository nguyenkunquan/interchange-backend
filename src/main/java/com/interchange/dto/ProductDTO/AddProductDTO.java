package com.interchange.dto.ProductDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AddProductDTO {
    @NotBlank(message = "The product name can not null")
    private String productName;
    @NotBlank(message = "The product description can not null")
    private String productDescription;
    @NotBlank(message = "The product color can not null")
    private String productColor;
    @NotBlank(message = "The Measure Unit ID can not null")
    private int measureUnitId;
    @NotBlank(message = "The product length can not null")
    private double length;
    @NotBlank(message = "The product height can not null")
    private double height;
    @NotBlank(message = "The product width can not null")
    private double width;
    @NotBlank(message = "The category name can not null")
    private String categoryName;
    @NotBlank(message = "The material name can not null")
    private String materialName;
    @NotBlank(message = "The room category can not null")
    private String roomCategoryName;
    @NotBlank(message = "The Unit Price of An Cuong supplier can not null")
    private double unitPriceAnCuongSupplier;
    @NotBlank(message = "The Unit Price of Thanh Thuy supplier can not null")
    private double unitPriceThanhThuySupplier;
    @NotBlank(message = "The Unit Price of Moc Phat supplier can not null")
    private double unitPriceSupplerMocPhatSupplier;
}
