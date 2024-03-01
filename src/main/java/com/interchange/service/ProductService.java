package com.interchange.service;

import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<?> findAll();

    ResponseEntity<?> findAllProductDetailByRoomId(int roomId);
    ResponseEntity<?> inputCustomProduct(int proId, int supId);
    ResponseEntity<?> isCustomizedByProId(int proId);
    ResponseEntity<?> findAllProductByRoomCategoryId(int roomCategoryId);
}
