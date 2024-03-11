package com.interchange.dto.CategoryProductDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AddCategoryProductDTO {
    private String categoryName;
    private List<Integer> materials;
}
