package com.interchange.converter;

import com.interchange.dto.SupplierDTO.SupplierDTO;
import com.interchange.entities.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierConverter {
    public Supplier toSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setSupName(supplierDTO.getSupplierName());
        supplier.setSupAddress(supplierDTO.getSupplierAddress());
        supplier.setSupPhone(supplierDTO.getSupplierPhone());
        return supplier;
    }
}
