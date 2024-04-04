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

    @Query(value = "SELECT  sp.supplier_product_id, sp.sup_id, s.sup_name, p.pro_id, p.pro_name, sp.unit_price FROM supplier_product sp " +
            "JOIN supplier s ON sp.sup_id = s.sup_id " +
            "JOIN product p ON  sp.pro_id = p.pro_id WHERE sp.sup_id = ?1", nativeQuery = true)
    List<Map<String, Object>> listSupplierProductBySupId(int supId);

    @Query(value = "SELECT * FROM supplier_product WHERE pro_id = ?1 AND sup_id = ?2", nativeQuery = true)
    SupplierProduct getFirstByProIdAndSupId(int proId, int supId);

    SupplierProduct findBySupplier_SupIdAndProduct_ProId(int supId, int proId);

    @Query(value = "SELECT COUNT(*) FROM supplier_product WHERE sup_id = ?1", nativeQuery = true)
    Integer countProductBySupplierID(int supId);
}
