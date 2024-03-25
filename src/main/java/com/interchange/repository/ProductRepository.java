package com.interchange.repository;

import com.interchange.dto.ProductDTO.ListProductDTO;
import com.interchange.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT pd.pro_detail_id, p.pro_name, s.sup_name, cp.category_name, m.material_name, p.pro_color,\n" +
            "       pd.pro_length, pd.pro_width, pd.pro_height, pd.pro_price\n" +
            "    FROM room_product rp\n" +
            "    JOIN product_detail pd ON rp.room_id = ? and rp.pro_detail_id = pd.pro_detail_id\n" +
            "    JOIN supplier_product sp ON pd.supplier_product_id = sp.supplier_product_id\n" +
            "    JOIN supplier s ON sp.sup_id = s.sup_id\n" +
            "    JOIN product p ON sp.pro_id = p.pro_id\n" +
            "    JOIN category_material cm ON p.category_material_id = cm.category_material_id\n" +
            "    JOIN category_product cp ON cm.pro_category_id = cp.pro_category_id\n" +
            "    JOIN material m ON cm.material_id = m.material_id" ,nativeQuery = true)
    List<Map<String, Objects>> findAllProductDetailByRoomId(int roomId);

    @Query(value = "SELECT sp.supplier_product_id, p.pro_name, p.pro_description, pd.pro_length, pd.pro_width, pd.pro_height,\n" +
            "       mu.measure_unit_name, mu.is_cus_length, mu.is_cus_width, mu.is_cus_height\n" +
            "                 , pd.pro_price, sp.unit_price\n" +
            "            FROM product p\n" +
            "                JOIN measure_unit mu ON p.measure_unit_id = mu.measure_unit_id\n" +
            "                    JOIN category_material cm ON p.category_material_id = cm.category_material_id and p.pro_id = ?1\n" +
            "                     JOIN material m ON cm.material_id = m.material_id\n" +
            "                    JOIN category_product cp ON cp.pro_category_id = cm.pro_category_id\n" +
            "                     JOIN supplier_product sp ON p.pro_id = sp.pro_id\n" +
            "                     JOIN supplier s ON sp.sup_id = s.sup_id and s.sup_id = ?2\n" +
            "                     JOIN product_detail pd ON sp.supplier_product_id = pd.supplier_product_id LIMIT 1", nativeQuery = true)
    Map<String, Objects> inputSupplierProduct(int proId, int supId);

    @Query(value = "SELECT p.pro_id, p.pro_name, ic.is_cus_height FROM product p\n" +
            "    JOIN category_material cm ON p.pro_id = ? and p.category_material_id= cm.category_material_id\n" +
            "    JOIN category_product cp ON cm.pro_category_id = cp.pro_category_id\n" +
            "    JOIN is_customized ic ON cp.is_customized_id = ic.is_customized_id", nativeQuery = true)
    Map<String, Object> isCustomizedByProId(int proId);

    @Query(value = "SELECT p.pro_id  as productId, p.pro_name as productName, cp.category_name as categoryName, m.material_name as materialName " +
            "FROM product p\n" +
            "JOIN category_material cm ON p.category_material_id = cm.category_material_id\n" +
            "JOIN category_product cp ON cm.pro_category_id = cp.pro_category_id\n" +
            "JOIN material m ON cm.material_id = m.material_id", nativeQuery = true)
    List<Map<String, Objects>> listProduct();

    Product findFirstByProId(int proId);

    @Query(value = "SELECT p.pro_name, p.pro_description, p.pro_color, mu.measure_unit_name, c.category_name AS product_category_name, m.material_name, cr.category_name FROM product p\n" +
            "JOIN measure_unit mu ON p.measure_unit_id = mu.measure_unit_id\n" +
            "JOIN category_room cr ON p.room_category_id = cr.room_category_id\n" +
            "JOIN category_material cm ON p.category_material_id = cm.category_material_id\n" +
            "JOIN category_product c ON cm.pro_category_id = c.pro_category_id\n" +
            "JOIN material m ON cm.material_id = m.material_id\n" +
            "WHERE p.pro_id =?1", nativeQuery = true)
    Map<String, Object> getProductInfoById(int proId);


}
