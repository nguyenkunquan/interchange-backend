package com.interchange.controller;

import com.interchange.dto.SupplierDTO.SupplierDTO;
import com.interchange.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @GetMapping("/listSupplier")
    public ResponseEntity<?> findAll(){
        return supplierService.findAllSupplier();
    }
    @GetMapping("/listSupplierProduct/{supplierId}")
    public ResponseEntity<?> findAllSupplierProduct(@PathVariable("supplierId") int supplierId){
        return supplierService.findAllSupplierProduct(supplierId);
    }
    @PostMapping("/save")
    public  ResponseEntity<?> addSuppler(@RequestBody SupplierDTO supplierDTO){
        return supplierService.addSuppler(supplierDTO);
    }
    @PutMapping("/update/{supplierId}")
    public ResponseEntity<?> updateSupplier(@PathVariable int supplierId, @RequestBody SupplierDTO supplierDTO){
        return supplierService.updateSupplier(supplierId, supplierDTO);
    }
}
