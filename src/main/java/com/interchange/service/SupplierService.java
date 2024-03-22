package com.interchange.service;

import com.interchange.dto.SupplierDTO.SupplierDTO;
import org.springframework.http.ResponseEntity;

public interface SupplierService {
    ResponseEntity<?> findAllSupplier();
    ResponseEntity<?> addSuppler(SupplierDTO supplierDTO);
    ResponseEntity<?> findAllSupplierProduct(int supplierId);
    ResponseEntity<?> updateSupplier(int supplierId, SupplierDTO supplierDTO);
    ResponseEntity<?> findSupplierById(int supplierId);
}
