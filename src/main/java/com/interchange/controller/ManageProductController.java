package com.interchange.controller;

import com.interchange.dto.ProductDTO.AddProductDTO;
import com.interchange.service.ProductService;
import com.interchange.service.SupplierProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin("*")
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
    public ResponseEntity<?> addProduct(@Valid @RequestBody AddProductDTO addProductDTO) {
        return productService.AddProduct(addProductDTO);
    }
    @GetMapping("/listSupplierProduct/{proId}")
    public ResponseEntity<?> listSupplierProduct(@PathVariable int proId) {
        return supplierProductService.listSupplierProduct(proId);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
