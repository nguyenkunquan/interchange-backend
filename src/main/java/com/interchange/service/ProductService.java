package com.interchange.service;

import com.interchange.dto.ProductDTO.AddProductDTO;
import com.interchange.dto.ProductDTO.ListProductDTO;
import com.interchange.dto.ProductDTO.UpdateProductDTO;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<?> findAll();

    ResponseEntity<?> findAllProductDetailByRoomId(int roomId);
    ResponseEntity<?> inputSupplierProduct(int proId, int supId);
    ResponseEntity<?> findAllProductByRoomCategoryId(int roomCategoryId);

    ResponseEntity<?> listProduct();

    ResponseEntity<?> addProduct(AddProductDTO addProductDTO);

    ResponseEntity<?> getProductById(int proId);

    ResponseEntity<?> getProductInfoById(int proId);

    ResponseEntity<?> updateProduct(int proId, UpdateProductDTO updateProductDTO);

}
