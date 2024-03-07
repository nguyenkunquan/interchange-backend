package com.interchange.service;

import com.interchange.dto.MaterialDTO.MaterialDTO;
import com.interchange.entities.Material;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MaterialService {
    List<Material> getAllMaterials();
    ResponseEntity<?> addMaterial(MaterialDTO materialDTO);
    ResponseEntity<?> updateMaterial(int materialId, MaterialDTO materialDTO);
}
