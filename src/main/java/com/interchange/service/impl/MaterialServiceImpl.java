package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.converter.MaterialConverter;
import com.interchange.dto.MaterialDTO.MaterialDTO;
import com.interchange.entities.Material;
import com.interchange.repository.MaterialRepository;
import com.interchange.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl extends BaseResponse implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialConverter materialConverter;
    @Override
    public ResponseEntity<?> getAllMaterials() {
        return getResponseEntity(materialRepository.findAll());
    }

    @Override
    public ResponseEntity<?> addMaterial(MaterialDTO materialDTO) {
        if(materialDTO.getMaterialName() == null || materialDTO.getMaterialName().isEmpty()) {
            return new ResponseEntity<>("Material name is required!", HttpStatus.BAD_REQUEST);
        }
        else {
            Material material = materialConverter.toMaterial(materialDTO);
            materialRepository.save(material);
            return getResponseEntity("Add material successfully!");
        }
    }

    @Override
    public ResponseEntity<?> updateMaterial(int materialId, MaterialDTO materialDTO) {
        Material material = materialRepository.findFirstByMaterialId(materialId);
        if (material == null) {
            return new ResponseEntity<>("Material not found!", HttpStatus.NOT_FOUND);
        }
        else {
            material = materialConverter.toMaterial(materialDTO, material);
            materialRepository.save(material);
            return getResponseEntity("Update material successfully!");
        }
    }

    @Override
    public ResponseEntity<?> getMaterialById(int materialId) {
        return getResponseEntity(materialRepository.findFirstByMaterialId(materialId));
    }
}
