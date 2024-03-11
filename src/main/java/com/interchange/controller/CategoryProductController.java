package com.interchange.controller;

import com.interchange.dto.CategoryProductDTO.AddCategoryProductDTO;
import com.interchange.dto.ProductDTO.AddProductDTO;
import com.interchange.service.CategoryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categoryProduct")
public class CategoryProductController {
    @Autowired
    private CategoryProductService categoryProductService;

    @GetMapping("/listCategoryProducts")
    public ResponseEntity<?> listCategoryProducts() {
        return categoryProductService.getAllCategoryProducts();
    }

    @PostMapping("/addCategoryProduct")
    public ResponseEntity<?> addCategoryProduct(@RequestBody AddCategoryProductDTO addCategoryProductDTO) {
        return categoryProductService.addCategoryProduct(addCategoryProductDTO);
    }

    @PutMapping("/updateCategoryProduct/{catProId}")
    public ResponseEntity<?> updateCategoryProduct(@PathVariable int catProId, @RequestBody AddCategoryProductDTO addCategoryProductDTO) {
        return categoryProductService.updateCategoryProduct(catProId, addCategoryProductDTO);
    }
}
