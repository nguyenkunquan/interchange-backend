package com.interchange.controller;

import com.interchange.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @GetMapping()
    public ResponseEntity<?> findAll(){
        return supplierService.findAll();
    }
}
