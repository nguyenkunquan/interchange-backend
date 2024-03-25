package com.interchange.dto.MaterialDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MaterialDTO {
    @NotBlank(message = "Material name is required")
    private String materialName;
}
