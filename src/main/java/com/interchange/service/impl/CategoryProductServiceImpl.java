package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.dto.CategoryProductDTO.AddCategoryProductDTO;
import com.interchange.entities.CategoryMaterial;
import com.interchange.entities.CategoryProduct;
import com.interchange.entities.Material;
import com.interchange.repository.CategoryMaterialRepository;
import com.interchange.repository.CategoryProductRepository;
import com.interchange.repository.MaterialRepository;
import com.interchange.service.CategoryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Service
public class CategoryProductServiceImpl extends  BaseResponse implements CategoryProductService {
    @Autowired
    CategoryProductRepository categoryProductRepository;
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    CategoryMaterialRepository categoryMaterialRepository;
    @Override
    public ResponseEntity<?> getAllCategoryProducts() {
        return getResponseEntity(categoryProductRepository.findAll());
    }

    @Override
    public ResponseEntity<?> addCategoryProduct(AddCategoryProductDTO addCategoryProductDTO) {
        try {
            CategoryProduct categoryProduct = new CategoryProduct();
            categoryProduct.setCategoryName(addCategoryProductDTO.getCategoryName());
            categoryProductRepository.save(categoryProduct);
                for(int materialId : addCategoryProductDTO.getMaterials()) {
                    Material material = materialRepository.findFirstByMaterialId(materialId);
                    CategoryMaterial categoryMaterial = new CategoryMaterial();
                    categoryMaterial.setCategoryProduct(categoryProduct);
                    categoryMaterial.setMaterial(material);
                    categoryMaterialRepository.save(categoryMaterial);
                }
                return getResponseEntity("Category Product Added Successfully");
        } catch (Exception e) {
            return getResponseEntity(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> updateCategoryProduct(int proCategoryId, AddCategoryProductDTO addCategoryProductDTO) {
        try {
            CategoryProduct categoryProduct = categoryProductRepository.findFirstByProCategoryId(proCategoryId);
            categoryProduct.setCategoryName(addCategoryProductDTO.getCategoryName());
            categoryProductRepository.save(categoryProduct);
            List<Integer> materialIds = addCategoryProductDTO.getMaterials();
            if(materialIds != null) {
                for (int materialId : materialIds) {
                    Material material = materialRepository.findFirstByMaterialId(materialId);
                    CategoryMaterial categoryMaterial = categoryMaterialRepository
                            .findCategoryMaterialByCategoryProductAndMaterial(proCategoryId, materialId);
                    if (categoryMaterial == null) {
                        categoryMaterial = new CategoryMaterial();
                        categoryMaterial.setCategoryProduct(categoryProduct);
                        categoryMaterial.setMaterial(material);
                        categoryMaterialRepository.save(categoryMaterial);
                    }
                }
            }
            return getResponseEntity("Category Product Updated Successfully");
        } catch (Exception e) {
            return getResponseEntity(e.getMessage());
        }
    }
}
