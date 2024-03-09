package com.interchange.controller;

import com.interchange.dto.ProductDTO.AddProductDTO;
import com.interchange.dto.SupplierProductDTO.UpdateUnitPriceDTO;
import com.interchange.service.ProductService;
import com.interchange.service.SupplierProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/manage-product")
public class ManageProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private SupplierProductService supplierProductService;
    @GetMapping("/listProduct")
    public ResponseEntity<?> list() {
        return productService.listProduct();
    }
    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody AddProductDTO addProductDTO) {
        return productService.AddProduct(addProductDTO);
    }
    @GetMapping("/listSupplierProduct/{proId}")
    public ResponseEntity<?> listSupplierProduct(@PathVariable int proId) {
        return supplierProductService.listSupplierProduct(proId);
    }
    @PutMapping("/updateUnitPrice/{supProId}")
    public ResponseEntity<?> updateUnitPrice(@PathVariable int supProId, @RequestBody UpdateUnitPriceDTO updateUnitPriceDTO) {
        return supplierProductService.updateUnitPrice(supProId, updateUnitPriceDTO);
    }

}
