package com.interchange.repository;

import com.interchange.entities.SupplierProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Integer> {
    SupplierProduct findBySupplier_SupIdAndProduct_ProId(int supId, int proId);
}
