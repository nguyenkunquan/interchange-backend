package com.interchange.controller;

import com.interchange.service.MeasureUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/measureUnit")
public class MeasureUnitController {
    @Autowired
    private MeasureUnitService measureUnitService;
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        return measureUnitService.findAll();
    }
}
