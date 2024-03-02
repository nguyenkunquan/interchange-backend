package com.interchange.controller;

import com.interchange.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<?> save() {
        return null;
    }

    @GetMapping()
    public ResponseEntity<?> findAll(){
        return productService.findAll();
    }

    @GetMapping("/productDetail")
    public ResponseEntity<?> findAllProductDetailByRoomId(@RequestParam int roomId) {
        return productService.findAllProductDetailByRoomId(roomId);
    }

    @GetMapping("/inputSupplierProduct")
    public ResponseEntity<?> inputSupplierProduct(@RequestParam int proId, int supId) {
        return productService.inputSupplierProduct(proId, supId);
    }

    @GetMapping("/roomCategory")
    public ResponseEntity<?> findAllProductByRoomCategoryId(@RequestParam int roomCategoryId) {
        return productService.findAllProductByRoomCategoryId(roomCategoryId);
    }
}
