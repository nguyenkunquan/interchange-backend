package com.interchange.dto.CategoryProductDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class AddCategoryProductDTO {
    @NotBlank(message = "Category name is required")
    private String categoryName;

    private List<Integer> materials;
}
