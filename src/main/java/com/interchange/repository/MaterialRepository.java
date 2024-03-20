package com.interchange.repository;

import com.interchange.entities.Material;
import com.interchange.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    Material findFirstByMaterialId(int materialId);

    Material findMaterialByMaterialName(String materialName);

    @Query(value = "SELECT m.material_id, m.material_name FROM category_product as cp " +
            "JOIN category_material as cm on cp.pro_category_id = cm.pro_category_id " +
            "JOIN material as m ON cm.material_id = m.material_id " +
            "WHERE cp.pro_category_id = ?1", nativeQuery = true)
    List<Map<String, Object>> getAllMaterialsByProCategoryId(int proCategoryId);
}
