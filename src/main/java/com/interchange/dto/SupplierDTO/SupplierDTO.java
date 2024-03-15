package com.interchange.dto.SupplierDTO;

import lombok.Data;

import java.util.Map;
@Data
public class SupplierDTO {
    private String supplierName;
    private String supplierAddress;
    private String supplierPhone;
    private Map<Integer, Double> unitPrices;
}
