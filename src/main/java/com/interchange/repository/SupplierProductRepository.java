package com.interchange.repository;

import com.interchange.entities.SupplierProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Integer> {
    @Query(value = "SELECT sp.sup_id, s.sup_name, p.pro_name, sp.unit_price, sp.supplier_product_id FROM supplier_product sp " +
            "JOIN supplier s ON sp.sup_id = s.sup_id " +
            "JOIN product p ON  sp.pro_id = p.pro_id WHERE p.pro_id = ?1", nativeQuery = true)
    List<Map<String, Object>> listSupplierProduct(int proId);
    SupplierProduct getFirstBySupplierProductId(int supplierProductId);
}
