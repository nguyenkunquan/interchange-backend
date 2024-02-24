package com.interchange.repository;

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

    //List<Map>

}
