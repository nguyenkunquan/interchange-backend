package com.interchange.dto.ProductDTO;

import lombok.Data;

@Data
public class ListProductDTO {
    private int productId;
    private String productName;
    private String materialName;
    private String categoryName;
}
