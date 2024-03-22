package com.interchange.controller;

import com.interchange.dto.MaterialDTO.MaterialDTO;
import com.interchange.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*") // Allow all origins
@RequestMapping("/api/materials")
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    @GetMapping("/listMaterials")
    public ResponseEntity<?> listMaterials() {
        return materialService.getAllMaterials();
    }
    @GetMapping("/getMaterial/{materialId}")
    public ResponseEntity<?> getMaterial(@PathVariable int materialId) {
        return materialService.getMaterialById(materialId);
    }
    @PostMapping("/addMaterial")
    public ResponseEntity<?> addMaterial(@Valid @RequestBody MaterialDTO materialDTO) {
        return materialService.addMaterial(materialDTO);
    }
    @PutMapping("/updateMaterial/{materialId}")
    public ResponseEntity<?> updateMaterial(@PathVariable int materialId, @Valid @RequestBody MaterialDTO materialDTO) {
        return materialService.updateMaterial(materialId, materialDTO);
    }
}
