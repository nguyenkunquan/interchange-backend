package com.interchange.dto.SupplierDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;
@Data
public class SupplierDTO {
    @NotBlank(message = "Supplier name is required")
    private String supplierName;
    @NotBlank(message = "Supplier address is required")
    private String supplierAddress;
    @NotBlank(message = "Supplier phone is required")
    private String supplierPhone;
    @NotBlank(message = "Unit Prices is required")
    private Map<Integer, Double> unitPrices;
}
