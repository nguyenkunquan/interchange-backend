package com.interchange.dto.ProductDTO;

import lombok.Data;

@Data
public class AddProductDTO {
    private String productName;
    private String productDescription;
    private String productColor;

    private int measureUnitId;
    private double length;
    private double height;
    private double width;

    private String categoryName;
    private String materialName;
    private String roomCategoryName;
    private String supplierName1;
    private String supplierName2;
    private String supplierName3;
    private double unitPriceAnCuongSupplier;
    private double unitPriceThanhThuySupplier;
    private double unitPriceSupplerMocPhatSupplier;
}
