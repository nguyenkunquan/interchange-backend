package com.interchange.repository;

import com.interchange.entities.Material;
import com.interchange.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    Material findFirstByMaterialId(int materialId);
}
