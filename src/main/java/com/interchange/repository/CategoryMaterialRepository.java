package com.interchange.repository;

import com.interchange.entities.CategoryMaterial;
import com.interchange.entities.CategoryProduct;
import com.interchange.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CategoryMaterialRepository extends JpaRepository<CategoryMaterial, Integer> {
//    @Query(value = "SELECT * FROM CategoryMaterial WHERE materialId = ?1 AND categoryId = ?2", nativeQuery = true)
//    CategoryMaterial findCategoryMaterialByMaterialIdAAndCategoryId(int materialId, int categoryId);

    @Query(value = "SELECT cm.* FROM  category_material cm " +
            "JOIN category_product cp ON cm.pro_category_id = cp.pro_category_id " +
            "JOIN material m ON cm.material_id = m.material_id " +
            "WHERE cp.pro_category_id = ?1 AND m.material_id = ?2", nativeQuery = true)
    CategoryMaterial findCategoryMaterialByCategoryProductAndMaterial(int categoryId, int materialId);

    List<CategoryMaterial> findAllByCategoryProduct(CategoryProduct categoryProduct);
}
