package com.interchange.service;

import org.springframework.http.ResponseEntity;

public interface SupplierProductService {
    ResponseEntity<?> listSupplierProduct(int proId);

    ResponseEntity<?> listCountProductBySupplierID();
}
