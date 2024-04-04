package com.interchange.controller;

import com.interchange.dto.SupplierDTO.SupplierDTO;
import com.interchange.service.SupplierProductService;
import com.interchange.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @Autowired
    SupplierProductService supplierProductService;

    @GetMapping("/listSupplier")
    public ResponseEntity<?> findAll(){
        return supplierService.findAllSupplier();
    }
    @GetMapping()
    public ResponseEntity<?> findAll2(){
        return supplierService.findAllSupplier();
    }
    @GetMapping("/findSupplierById/{supplierId}")
    public ResponseEntity<?> findSupplierById(@PathVariable("supplierId") int supplierId){
        return supplierService.findSupplierById(supplierId);
    }
    @GetMapping("/listSupplierProduct/{supplierId}")
    public ResponseEntity<?> findAllSupplierProduct(@PathVariable("supplierId") int supplierId){
        return supplierService.findAllSupplierProduct(supplierId);
    }
    @PostMapping("/save")
    public  ResponseEntity<?> addSuppler(@Valid @RequestBody SupplierDTO supplierDTO){
        return supplierService.addSuppler(supplierDTO);
    }
    @PutMapping("/update/{supplierId}")
    public ResponseEntity<?> updateSupplier(@PathVariable int supplierId,@Valid @RequestBody SupplierDTO supplierDTO){
        return supplierService.updateSupplier(supplierId, supplierDTO);
    }

    @GetMapping("/countProductBySupplier")
    public ResponseEntity<?> countProductBySupplier(){
        return supplierProductService.listCountProductBySupplierID();
    }



}
