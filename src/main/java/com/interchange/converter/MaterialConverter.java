package com.interchange.converter;

import com.interchange.dto.MaterialDTO.MaterialDTO;
import com.interchange.entities.Material;
import org.springframework.stereotype.Component;

@Component
public class MaterialConverter {
    public Material toMaterial(MaterialDTO materialDTO) {
        Material material = new Material();
        material.setMaterialName(materialDTO.getMaterialName());
        return material;
    }
    public Material toMaterial(MaterialDTO materialDTO, Material material) {
        material.setMaterialName(materialDTO.getMaterialName());
        return material;
    }
}
