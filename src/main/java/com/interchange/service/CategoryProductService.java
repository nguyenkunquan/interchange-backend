package com.interchange.service;

import com.interchange.dto.CategoryProductDTO.AddCategoryProductDTO;
import com.interchange.entities.CategoryProduct;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryProductService {
    ResponseEntity<?> getAllCategoryProducts();

    ResponseEntity<?> addCategoryProduct(AddCategoryProductDTO addCategoryProductDTO);

    ResponseEntity<?> updateCategoryProduct(int catProId, AddCategoryProductDTO addCategoryProductDTO);

    ResponseEntity<?> getAllMaterialsByProCategoryId(int proCategoryId);

    ResponseEntity<?> getCategoryProductById(int catProId);
}
