package com.interchange.service;

import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<?> findAll();

    ResponseEntity<?> findAllProductDetailByRoomId(int roomId);
    ResponseEntity<?> inputSupplierProduct(int proId, int supId);
    ResponseEntity<?> findAllProductByRoomCategoryId(int roomCategoryId);
}
