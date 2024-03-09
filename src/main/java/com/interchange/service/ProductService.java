package com.interchange.service;

import com.interchange.dto.ProductDTO.AddProductDTO;
import com.interchange.dto.ProductDTO.ListProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<?> findAll();

    ResponseEntity<?> findAllProductDetailByRoomId(int roomId);
    ResponseEntity<?> inputSupplierProduct(int proId, int supId);
    ResponseEntity<?> findAllProductByRoomCategoryId(int roomCategoryId);

    ResponseEntity<?> listProduct();

    ResponseEntity<?> AddProduct(AddProductDTO addProductDTO);

}
