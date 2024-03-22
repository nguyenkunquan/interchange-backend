package com.interchange.controller;

import com.interchange.dto.CategoryProductDTO.AddCategoryProductDTO;
import com.interchange.dto.ProductDTO.AddProductDTO;
import com.interchange.service.CategoryProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/categoryProduct")
public class CategoryProductController {
    @Autowired
    private CategoryProductService categoryProductService;

    @GetMapping("/listCategoryProducts")
    public ResponseEntity<?> listCategoryProducts() {
        return categoryProductService.getAllCategoryProducts();
    }

    @GetMapping("/listCategoryProductsByProId/{proId}")
    public ResponseEntity<?> listCategoryProductsByProId(@PathVariable int proId) {
        return categoryProductService.getCategoryProductById(proId);
    }
    @PostMapping("/addCategoryProduct")
    public ResponseEntity<?> addCategoryProduct(@RequestBody @Valid AddCategoryProductDTO addCategoryProductDTO) {
        return categoryProductService.addCategoryProduct(addCategoryProductDTO);
    }
    @PutMapping("/updateCategoryProduct/{catProId}")
    public ResponseEntity<?> updateCategoryProduct(@PathVariable int catProId,@Valid @RequestBody AddCategoryProductDTO addCategoryProductDTO) {
        return categoryProductService.updateCategoryProduct(catProId, addCategoryProductDTO);
    }

    @GetMapping("/listMaterialsByProCategoryId/{proCategoryId}")
    public ResponseEntity<?> listMaterialsByProCategoryId(@PathVariable int proCategoryId) {
        return categoryProductService.getAllMaterialsByProCategoryId(proCategoryId);
    }
}
